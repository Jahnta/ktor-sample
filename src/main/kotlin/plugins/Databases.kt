package plugins

import com.example.data.area.AreaTable
import com.example.data.equipment.EquipmentTable
import com.example.data.event.EventTable
import com.example.data.event_workscope.EventWorkscopeTable
import com.example.data.organization.OrganizationTable
import com.example.data.powerplant.PowerPlantTable
import com.example.data.powerunit.PowerUnitTable
import com.example.data.workscope.WorkscopeTable
import common.*
import io.ktor.server.application.*
import org.jetbrains.exposed.v1.core.StdOutSqlLogger
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.io.File

private const val DB_PATH = "./data/myapp.db"

fun Application.configureDatabases() {
    File("./data").mkdirs()

    Database.connect(
        url = "jdbc:sqlite:$DB_PATH",
        driver = "org.sqlite.JDBC",
        setupConnection = { connection ->
            connection.createStatement().use { stmt ->
                stmt.execute("PRAGMA foreign_keys = ON;")
            }
        }
    )

    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.drop(
            AreaTable,
            OrganizationTable,
            PowerPlantTable,
            PowerUnitTable,
            EquipmentTable,
            EventTable,
            WorkscopeTable,
            EventWorkscopeTable,
        )
        SchemaUtils.create(
            AreaTable,
            OrganizationTable,
            PowerPlantTable,
            PowerUnitTable,
            EquipmentTable,
            EventTable,
            WorkscopeTable,
            EventWorkscopeTable,
        )

        initAreas()
        initOrganizations()
        initPowerPlants()
        initPowerUnits()
        initEquipment()
        initEvents()
        initWorkscopes()
        initEventWorkscopes()
    }
}


suspend fun <T> newSuspendTransaction(block: suspend Transaction.() -> T): T = suspendTransaction {
    block()
}
