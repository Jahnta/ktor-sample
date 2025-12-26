const childrenState = {};

function escapeHtml(text) {
    return text?.toString().replace(/&/g, "&amp;").replace(/</g, "&lt;")
        .replace(/>/g, "&gt;").replace(/"/g, "&quot;").replace(/'/g, "&#039;") || "";
}

function renderEntity(entity, config, configKey) {
    const card = document.createElement('section');
    card.className = 'card';

    card.onclick = (e) => {
        // Чтобы кнопки редактирования/удаления не срабатывали
        if (!e.target.closest('button')) {
            window.location.href = `/${configKey}/${entity.id}`;
        }
    };

    card.innerHTML = `
        <div class="card-header">
            <div class="card-title">${escapeHtml(entity.name)} (id=${entity.id})</div>
            <div>
                <button class="secondary" onclick="openEditEntity('${configKey}', ${entity.id})">✏️</button>
                <button class="danger" onclick="openDeleteEntity('${configKey}', ${entity.id}, '${escapeHtml(entity.name)}')">🗑</button>
            </div>
        </div>
        <div class="card-info">
            ${config.fields.map(f => infoRow(f.label, entity[f.name])).join('')}
        </div>
    `;

    // Вложенные сущности
    if(config.nested && entity[config.nested.key]?.length) {
        const nested = document.createElement('div');
        nested.className = 'nested';
        nested.innerHTML = `
            <div class="nested-header">Связанные элементы <span>${entity[config.nested.key].length}</span></div>
            <div class="nested-content"></div>
        `;
        const contentDiv = nested.querySelector('.nested-content');
        const nestedConfig = entitiesConfig[config.nested.configKey];
        entity[config.nested.key].forEach(child => contentDiv.appendChild(renderEntity(child, nestedConfig, config.nested.configKey)));
        const header = nested.querySelector('.nested-header');
        const key = `${configKey}-${entity.id}`;
        if(childrenState[key]) nested.classList.add('open');
        header.onclick = (e) => { e.stopPropagation(); toggleChildren(nested, key); };
        card.appendChild(nested);
    }

    return card;
}

function infoRow(label, value) {
    if(!value) return '';
    return `<div class="card-info-label">${label}</div><div>${escapeHtml(value)}</div>`;
}

function toggleChildren(container, key) {
    const content = container.querySelector('.nested-content');
    if(container.classList.contains('open')){
        content.style.maxHeight = '0';
        container.classList.remove('open');
        childrenState[key] = false;
    } else {
        content.style.maxHeight = content.scrollHeight + 'px';
        container.classList.add('open');
        childrenState[key] = true;
    }
}

// Динамическая генерация формы
function buildForm(config, entity) {
    const formFields = document.getElementById('formFields');
    formFields.innerHTML = '';
    config.fields.forEach(f => {
        const value = entity?.[f.name] || '';
        formFields.innerHTML += `<label>${f.label}</label>
            <input type="${f.type}" id="${f.name}" ${f.required?'required':''} value="${escapeHtml(value)}">`;
    });
}
