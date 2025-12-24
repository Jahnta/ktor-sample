package common

import com.example.data.area.AreaEntity
import com.example.data.equipment.EquipmentEntity
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import com.example.data.organization.OrganizationEntity
import com.example.data.powerplant.PowerPlantEntity
import com.example.data.powerunit.PowerUnitEntity
import com.example.data.workscope.WorkscopeEntity
import com.example.plugins.JsonConfig
import data.event.EventEntity
import data.event_workscope.EventWorkscopeEntity
import kotlinx.datetime.LocalDate
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun initAreas() {
    if (AreaEntity.count() > 0) return

    val rows = readCsv("/data/areas.csv")
    val map = mutableMapOf<Int, AreaEntity>()

    rows.forEach { row ->
        val id = row["id"]!!.toInt()
        val entity = AreaEntity.new(id) {
            name = row["name"]!!
            parent = null
        }
        map[id] = entity
    }

    rows.forEach { row ->
        val id = row["id"]!!.toInt()
        val parentId = row["parent_id"]?.toInt()

        if (parentId != null) {
            map[id]!!.parent = map[parentId]
        }
    }
}

fun initOrganizations() {
    if (OrganizationEntity.count() > 0) return

    val rows = readCsv("/data/organizations.csv")
    val map = mutableMapOf<Int, OrganizationEntity>()

    rows.forEach { row ->
        val id = row["id"]!!.toInt()
        val entity = OrganizationEntity.new(id) {
            name = row["name"]!!
            shortName = row["short_name"]
            parent = null

            area = row["area_id"]?.toInt()?.let { AreaEntity.findById(it) }
            address = row["address"]
            email = row["email"]
            website = row["website"]
            phoneNumber = row["phone_number"]
        }
        map[id] = entity
    }

    rows.forEach { row ->
        val id = row["id"]!!.toInt()
        val parentId = row["parent_id"]?.toInt()

        if (parentId != null) {
            map[id]!!.parent = map[parentId]
        }
    }
}

fun initPowerPlants() {
    if (PowerPlantEntity.count() > 0) return

    val rows = readCsv("/data/power_plants.csv")
    val map = mutableMapOf<Int, PowerPlantEntity>()

    rows.forEach { row ->
        val id = row["id"]!!.toInt()
        val entity = PowerPlantEntity.new(id) {
            name = row["name"]!!
            shortName = row["short_name"]
            parent = row["parent_id"]?.toInt()?.let { OrganizationEntity.findById(it) }

            area = row["area_id"]?.toInt()?.let { AreaEntity.findById(it) }
            address = row["address"]
            email = row["email"]
            website = row["website"]
            phoneNumber = row["phone_number"]

            type = row["type"]
            electricalPower = row["electrical_power"]?.toDouble()
            thermalPower = row["thermal_power"]?.toDouble()
        }
        map[id] = entity
    }
}

fun initPowerUnits() {
    if (PowerUnitEntity.count() > 0) return

    val rows = readCsv("/data/power_units.csv")
    val map = mutableMapOf<Int, PowerUnitEntity>()

    rows.forEach { row ->
        val id = row["id"]!!.toInt()
        val entity = PowerUnitEntity.new(id) {
            name = row["name"]!!
            shortName = row["short_name"]
            powerPlant = row["plant_id"]?.toInt()?.let { PowerPlantEntity.findById(it) }

            type = row["type"]
            capacity = row["capacity"]?.toDouble()
        }
        map[id] = entity
    }
}

fun initEquipment() {
    if (EquipmentEntity.count() > 0) return

    val rows = readCsv("/data/equipment.csv")
    val map = mutableMapOf<Int, EquipmentEntity>()

    rows.forEach { row ->
        val id = row["id"]!!.toInt()
        val entity = EquipmentEntity.new(id) {
            name = row["name"]!!
            shortName = row["short_name"]
            powerUnit = row["power_unit_id"]?.toInt()?.let { PowerUnitEntity.findById(it) }

            type = row["type"]
            customAttributes = row["custom_attributes"]
        }
        map[id] = entity
    }
}

fun initEvents() {
    if (EventEntity.count() > 0) return

    val rows = readCsv("/data/events.csv")
    val map = mutableMapOf<Int, EventEntity>()

    rows.forEach { row ->
        val id = row["id"]!!.toInt()
        val entity = EventEntity.new(id) {
            name = row["name"]!!
            shortName = row["short_name"]
            equipment = row["equipment_id"]?.toInt()?.let { EquipmentEntity.findById(it) }

            dateStart = row["date_start"]?.let { LocalDate.parse(it) }
            dateEnd = row["date_end"]?.let { LocalDate.parse(it) }
            status = row["status"]

            type = row["type"]
            customAttributes = row["custom_attributes"]
        }
        map[id] = entity
    }
}

fun initWorkscopes() {
    if (WorkscopeEntity.count() > 0) return

    val rows = readCsv("/data/workscopes.csv")
    val map = mutableMapOf<Int, WorkscopeEntity>()

    rows.forEach { row ->
        val id = row["id"]!!.toInt()
        val entity = WorkscopeEntity.new(id) {
            name = row["name"]!!
            parent = null

            description = row["description"]
            defaultDuration = row["default_duration"]?.toInt()?.toDuration(DurationUnit.HOURS)

            customAttributes = row["custom_attributes"]
        }
        map[id] = entity
    }

    rows.forEach { row ->
        val id = row["id"]!!.toInt()
        val parentId = row["parent_id"]?.toInt()

        if (parentId != null) {
            map[id]!!.parent = map[parentId]
        }
    }
}

fun initEventWorkscopes() {
    if (EventWorkscopeEntity.count() > 0) return

    val rows = readCsv("/data/event_workscope.csv")
    val map = mutableMapOf<Int, EventWorkscopeEntity>()

    rows.forEach { row ->
        val id = row["id"]!!.toInt()
        val entity = EventWorkscopeEntity.new(id) {
            event = row["event_id"]?.toInt().let { EventEntity.findById(it!!) }!!
            workscope = row["workscope_id"]?.toInt().let { WorkscopeEntity.findById(it!!) }!!

            duration = row["duration"]?.toInt()?.toDuration(DurationUnit.HOURS)
            status = row["status"]

            customAttributes = row["custom_attributes"]
        }
        map[id] = entity
    }
}

fun readCsv(resourcePath: String): List<Map<String, String?>> {
    val stream = object {}.javaClass.getResourceAsStream(resourcePath)
        ?: error("CSV not found: $resourcePath")

    stream.bufferedReader().use { reader ->
        val format = CSVFormat.DEFAULT.builder()
            .setHeader()
            .setIgnoreSurroundingSpaces(true)
            .setTrim(true)
            .get()

        val parser = CSVParser.parse(reader, format)

        return parser.records.map { record ->
            parser.headerMap.keys.associateWith { header ->
                record.get(header).takeIf { it.isNotBlank() }
            }
        }
    }
}
