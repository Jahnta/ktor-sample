let configKey = "organizations";
let deleteId = null;

const sidebar = document.getElementById('sidebar');
const mainTitle = document.getElementById('mainTitle');
const content = document.getElementById('content');
const entityDialog = document.getElementById('entityDialog');
const deleteDialog = document.getElementById('deleteDialog');
const createBtn = document.getElementById('createBtn');
const cancelBtn = document.getElementById('cancelBtn');
const saveBtn = document.getElementById('saveBtn');
const deleteCancel = document.getElementById('deleteCancel');
const deleteConfirm = document.getElementById('deleteConfirm');

initSidebar();
loadEntitiesPage(configKey);

function initSidebar() {
    Object.keys(entitiesConfig).forEach(key => {
        const link = document.createElement('a');
        link.href="#";
        link.textContent = entitiesConfig[key].name;
        link.onclick = () => {
            document.querySelectorAll('nav a').forEach(a => a.classList.remove('active'));
            link.classList.add('active');
            loadEntitiesPage(key);
        };
        sidebar.appendChild(link);
    });
}

async function loadEntitiesPage(key) {
    configKey = key;
    const config = entitiesConfig[key];
    mainTitle.textContent = config.name;
    content.innerHTML = '';
    const data = await fetchEntities(config);
    data.forEach(item => content.appendChild(renderEntity(item, config, configKey)));
}

createBtn.onclick = () => openEditEntity(configKey);
cancelBtn.onclick = () => entityDialog.close();
saveBtn.onclick = () => saveEntityForm(configKey);
deleteCancel.onclick = () => deleteDialog.close();
deleteConfirm.onclick = () => confirmDeleteEntity(configKey);

function openEditEntity(key, id) {
    const config = entitiesConfig[key];
    if(id){
        fetchEntity(config, id).then(entity => {
            buildForm(config, entity);
            document.getElementById('dialogTitle').textContent = `Редактировать ${config.name}`;
            entityDialog.showModal();
            entityDialog.dataset.entityId = id;
        });
    } else {
        buildForm(config, {});
        document.getElementById('dialogTitle').textContent = `Создать ${config.name}`;
        entityDialog.showModal();
        delete entityDialog.dataset.entityId;
    }
}

function openDeleteEntity(key, id, name) {
    deleteId = id;
    deleteDialog.showModal();
    document.getElementById('deleteText').textContent = `${name} (${entitiesConfig[key].name})`;
}

async function saveEntityForm(key) {
    const config = entitiesConfig[key];
    const entity = {};
    config.fields.forEach(f => {
        const val = document.getElementById(f.name).value;
        entity[f.name] = f.type==="number"? (val?Number(val):null) : val || null;
    });
    if(entityDialog.dataset.entityId) entity.id = Number(entityDialog.dataset.entityId);
    const ok = await saveEntity(config, entity);
    if(ok) {
        entityDialog.close();
        loadEntitiesPage(key);
    } else alert("Ошибка сохранения");
}

async function confirmDeleteEntity(key) {
    const ok = await deleteEntity(entitiesConfig[key], deleteId);
    if(ok){
        deleteDialog.close();
        loadEntitiesPage(key);
    } else alert("Ошибка удаления");
}
