package common

import area.AreaEntity
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import organization.OrganizationEntity
import powerPlants.PowerPlantEntity
import powerUnits.PowerUnitEntity

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
            fullName = row["full_name"]!!
            shortName = row["short_name"]!!
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

    val rows = readCsv("/data/powerPlants.csv")
    val map = mutableMapOf<Int, PowerPlantEntity>()

    rows.forEach { row ->
        val id = row["id"]!!.toInt()
        val entity = PowerPlantEntity.new(id) {
            fullName = row["full_name"]!!
            shortName = row["short_name"]!!
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

    val rows = readCsv("/data/powerUnits.csv")
    val map = mutableMapOf<Int, PowerUnitEntity>()

    rows.forEach { row ->
        val id = row["id"]!!.toInt()
        val entity = PowerUnitEntity.new(id) {
            fullName = row["full_name"]!!
            shortName = row["short_name"]!!
            plant = row["plant_id"]?.toInt()?.let { PowerPlantEntity.findById(it) }

            type = row["type"]
            capacity = row["capacity"]?.toDouble()
        }
        map[id] = entity
    }
}

fun readCsv(resourcePath: String): List<Map<String, String?>> {
    val stream = object {}.javaClass.getResourceAsStream(resourcePath)
        ?: error("CSV not found: $resourcePath")

    stream.bufferedReader().use { reader ->
        val parser = CSVParser(
            reader,
            CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreSurroundingSpaces()
                .withTrim()
        )

        return parser.records.map { record ->
            parser.headerMap.keys.associateWith { header ->
                record.get(header).takeIf { it.isNotBlank() }
            }
        }
    }
}
