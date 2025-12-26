const entitiesConfig = {
    organizations: {
        name: "Организации",
        endpoint: "/organizations",
        fields: [
            { name: "name", label: "Название", type: "text", required: true },
            { name: "short_name", label: "Краткое название", type: "text" },
            { name: "parent_id", label: "Parent ID", type: "number" },
            { name: "area_id", label: "Area ID", type: "number" },
            { name: "address", label: "Адрес", type: "text" },
            { name: "email", label: "Email", type: "email" },
            { name: "website", label: "Website", type: "url" },
            { name: "phone_number", label: "Телефон", type: "text" }
        ],
        nested: { key: "organizations", configKey: "organizations" },// дочерние организации
    },
    "power-plants": {
        name: "Энергостанции",
        endpoint: "/power-plants",
        fields: [
            { name: "name", label: "Название", type: "text", required: true },
            { name: "short_name", label: "Краткое название", type: "text" },
            { name: "parent_id", label: "Parent ID", type: "number" },
            { name: "area_id", label: "Area ID", type: "number" },
            { name: "address", label: "Адрес", type: "text" },
            { name: "email", label: "Email", type: "email" },
            { name: "website", label: "Website", type: "url" },
            { name: "phone_number", label: "Телефон", type: "text" },
            { name: "type", label: "Тип", type: "text" },
            { name: "electrical_power", label: "Электрическая мощность", type: "number" },
            { name: "thermal_power", label: "Тепловая мощность", type: "number" }
        ],
        nested: { key: "power_units", configKey: "power-units" },
    },
    "power-units": {
        name: "Энергоблоки",
        endpoint: "/power-units",
        fields: [
            { name: "name", label: "Название", type: "text", required: true },
            { name: "short_name", label: "Краткое название", type: "text" },
            { name: "power_plant_id", label: "Энергостанция ID", type: "number" },
            { name: "type", label: "Тип", type: "text" },
            { name: "capacity", label: "Мощность", type: "number" }
        ],
        nested: { key: "equipment", configKey: "equipment" },
    },
    equipment: {
        name: "Оборудование",
        endpoint: "/equipment",
        fields: [
            { name: "name", label: "Название", type: "text", required: true },
            { name: "short_name", label: "Краткое название", type: "text" },
            { name: "power_unit_id", label: "Энергоблок ID", type: "number" },
            { name: "type", label: "Тип", type: "text" }
            // customAttributes можно расширить позже
        ]
    },
    events: {
        name: "События",
        endpoint: "/events",
        fields: [
            { name: "name", label: "Название", type: "text", required: true },
            { name: "short_name", label: "Краткое название", type: "text" },
            { name: "equipment_id", label: "Оборудование ID", type: "number" },
            { name: "date_start", label: "Дата начала", type: "date" },
            { name: "date_end", label: "Дата окончания", type: "date" },
            { name: "status", label: "Статус", type: "text" },
            { name: "type", label: "Тип", type: "text" }
            // customAttributes можно добавить при необходимости
        ]
    }
};
