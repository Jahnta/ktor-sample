package plugins

import io.ktor.server.application.*
import org.jetbrains.exposed.v1.core.StdOutSqlLogger
import org.jetbrains.exposed.v1.jdbc.Database
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

//        SchemaUtils.drop(
//            AreaTable,
//            OrganizationTable,
//            PowerPlantTable,
//            PowerUnitTable,
//            EquipmentTable
//        )
//        SchemaUtils.create(
//            AreaTable,
//            OrganizationTable,
//            PowerPlantTable,
//            PowerUnitTable,
//            EquipmentTable
//        )
//
//        initAreas()
//        initOrganizations()
//        initPowerPlants()
//        initPowerUnits()
//        initPowerUnits()
    }
}