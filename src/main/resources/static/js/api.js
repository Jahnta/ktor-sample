async function fetchEntities(config) {
    const res = await fetch(config.endpoint);
    return await res.json();
}

async function fetchEntity(config, id) {
    const res = await fetch(`${config.endpoint}/${id}`);
    return await res.json();
}

async function saveEntity(config, data) {
    const method = data.id ? "PUT" : "POST";
    const url = data.id ? `${config.endpoint}/${data.id}` : config.endpoint;
    const res = await fetch(url, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    });
    return res.ok;
}

async function deleteEntity(config, id) {
    const res = await fetch(`${config.endpoint}/${id}`, { method: "DELETE" });
    return res.ok;
}
