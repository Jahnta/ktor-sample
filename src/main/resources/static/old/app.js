/***********************
 * CONFIG
 ***********************/
const ENTITIES = {
    areas: {
        title: 'Areas',
        endpoint: 'areas',
        fields: [
            { id:'name', label:'Name' },
            { id:'parent_id', label:'Parent ID' }
        ]
    },
    organizations: {
        title: 'Organizations',
        endpoint: 'organizations',
        fields: [
            { id:'name', label:'Name' },
            { id:'short_name', label:'Short name' },
            { id:'parent_id', label:'Parent ID' },
            { id:'area_id', label:'Area ID' },
            { id:'address', label:'Address' },
            { id:'email', label:'Email' },
            { id:'website', label:'Website' },
            { id:'phone_number', label:'Phone' }
        ]
    },
    plants: {
        title: 'Power Plants',
        endpoint: 'power-plants',
        fields: [
            { id:'name', label:'Name' },
            { id:'short_name', label:'Short name' },
            { id:'parent_id', label:'Organization ID' },
            { id:'area_id', label:'Area ID' },
            { id:'type', label:'Type' },
            { id:'electrical_power', label:'Electrical power' },
            { id:'thermal_power', label:'Thermal power' },
            { id:'address', label:'Address' }
        ]
    },
    powerUnits: {
        title: 'Power Units',
        endpoint: 'power-units',
        fields: [
            { id:'name', label:'Name' },
            { id:'shortName', label:'Short name' },
            { id:'power_plant_id', label:'Plant ID' },
            { id:'type', label:'Type' },
            { id:'capacity', label:'Capacity' }
        ]
    },
    equipment: {
        title: 'Equipment',
        endpoint: 'equipment',
        fields: [
            { id:'name', label:'Name' },
            { id:'short_name', label:'Short name' },
            { id:'power_unit_id', label:'Power Unit ID' },
            { id:'type', label:'Type' },
            { id:'custom_attributes', label:'Custom attributes' }
        ]
    },
    events: {
        title: 'Events',
        endpoint: 'events',
        fields: [
            { id:'name', label: 'Name'},
            { id:'short_name', label:'Short name' },
            { id:'equipment_id', label:'Equipment ID' },
            { id:'date_start', label:'Date start' },
            { id:'date_end', label:'Date End' },
            { id:'status', label:'Status'},
            { id:'type', label:'Type' },
            { id:'custom_attributes', label:'Custom attributes' },
            { id:'workscope_items', label:'Workscope_items' }
        ]
    },
};

/***********************
 * STATE
 ***********************/
let currentEntity = null;
let modalAction = null;

/***********************
 * API
 ***********************/
const api = {
    get: url => fetch(url).then(r => r.json()),
    post: (url, body) => fetch(url, {
        method:'POST',
        headers:{'Content-Type':'application/json'},
        body: JSON.stringify(body)
    }),
    put: (url, body) => fetch(url, {
        method:'PUT',
        headers:{'Content-Type':'application/json'},
        body: JSON.stringify(body)
    }),
    del: url => fetch(url, {method:'DELETE'})
};

/***********************
 * UTILS
 ***********************/
function flattenTree(nodes) {
    return nodes.flatMap(n => [
        n,
        ...flattenTree([
            ...(n.areas||[]),
            ...(n.organizations||[]),
            ...(n.plants||[]),
            ...(n.powerUnits||[]),
            ...(n.equipment||[])
        ])
    ]);
}

/***********************
 * UI
 ***********************/
function renderMenu() {
    const menu = document.getElementById('menu');
    menu.innerHTML = `<h3>Справочники</h3>` +
        Object.keys(ENTITIES).map(k =>
            `<button data-entity="${k}">${ENTITIES[k].title}</button>`
        ).join('');

    menu.onclick = e => {
        if (e.target.dataset.entity) {
            loadEntity(e.target.dataset.entity);
        }
    };
}

async function loadEntity(key) {
    currentEntity = key;
    document.querySelectorAll('.menu button')
        .forEach(b => b.classList.toggle(
            'active',
            b.dataset.entity === key
        ));

    const entity = ENTITIES[key];
    const content = document.getElementById('content');

    content.innerHTML = `
        <h1>${entity.title}</h1>
        <button class="create-btn" id="createBtn">Создать</button>
        <div class="cards" id="cards"></div>
    `;

    document.getElementById('createBtn').onclick = () => openModal();

    const data = flattenTree(
        await api.get(`/${entity.endpoint}`)
    );
    renderCards(data);
}

function renderCards(data) {
    const cards = document.getElementById('cards');
    cards.innerHTML = '';

    data.forEach(item => {
        const div = document.createElement('div');
        div.className = 'card';

        div.innerHTML = `
          <div class="card-actions">
              <span class="icon edit" title="Редактировать">✎</span>
              <span class="icon delete" title="Удалить">🗑</span>
          </div>

          <h3>${item.name}</h3>
          <small>ID: ${item.id}</small>
        `;

        div.querySelector('.edit').onclick = () => openModal(item);
        div.querySelector('.delete').onclick = () => deleteItem(item.id);

        cards.appendChild(div);
    });
}

/***********************
 * MODAL
 ***********************/
function openModal(data={}) {
    const entity = ENTITIES[currentEntity];
    document.getElementById('modalTitle').textContent =
        data.id ? 'Редактировать' : 'Создать';

    document.getElementById('modalForm').innerHTML =
        entity.fields.map(f =>
            `<input id="f_${f.id}" placeholder="${f.label}" value="${data[f.id]||''}">`
        ).join('');

    modalAction = () => saveItem(data.id);
    document.getElementById('modal').style.display = 'flex';
}

function closeModal() {
    document.getElementById('modal').style.display = 'none';
}

async function saveItem(id) {
    const entity = ENTITIES[currentEntity];
    const body = { id: id || 0 };

    entity.fields.forEach(f => {
        body[f.id] = document.getElementById(`f_${f.id}`).value || null;
    });

    const url = `/${entity.endpoint}${id ? '/' + id : ''}`;
    id ? await api.put(url, body) : await api.post(url, body);

    closeModal();
    loadEntity(currentEntity);
}

async function deleteItem(id) {
    if (!confirm('Удалить запись?')) return;
    const entity = ENTITIES[currentEntity];
    await api.del(`/${entity.endpoint}/${id}`);
    loadEntity(currentEntity);
}

/***********************
 * INIT
 ***********************/
document.getElementById('modalClose').onclick = closeModal;
document.getElementById('modalSubmit').onclick = () => modalAction();

renderMenu();
loadEntity('organizations');
