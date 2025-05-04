document.addEventListener('DOMContentLoaded', function() {
    // Элементы интерфейса
    const tabs = {
        users: document.getElementById('users-tab'),
        detection: document.getElementById('detection-tab'),
        history: document.getElementById('history-tab'),
        stats: document.getElementById('stats-tab')
    };

    const sections = {
        users: document.getElementById('users-section'),
        detection: document.getElementById('detection-section'),
        history: document.getElementById('history-section'),
        stats: document.getElementById('stats-section')
    };

    // API endpoints
    const API = {
        users: '/api/users',
        detection: '/detect-language',
        history: '/api/detection-history',
        stats: '/api/statistics/requests'
    };

    // Переключение между вкладками
    for (const [tabName, tabElement] of Object.entries(tabs)) {
        tabElement.addEventListener('click', function() {
            // Сброс активных состояний
            for (const t of Object.values(tabs)) {
                t.classList.remove('active');
            }
            for (const s of Object.values(sections)) {
                s.classList.remove('active');
            }

            // Установка новых активных состояний
            tabElement.classList.add('active');
            sections[tabName].classList.add('active');

            // Загрузка данных при переключении
            switch(tabName) {
                case 'users':
                    loadUsers();
                    break;
                case 'history':
                    loadHistory();
                    break;
                case 'stats':
                    loadStatistics();
                    break;
            }
        });
    }

    // Инициализация приложения
    initApp();

    async function initApp() {
        await loadUsers();
        await loadHistory();
        await loadStatistics();
        setupEventListeners();
    }

    function setupEventListeners() {
        // Форма пользователя
        document.getElementById('add-user-btn').addEventListener('click', showUserForm);
        document.getElementById('cancel-user-btn').addEventListener('click', hideUserForm);
        document.getElementById('user-form').addEventListener('submit', handleUserSubmit);

        // Форма определения языка
        document.getElementById('detection-form').addEventListener('submit', handleDetectionSubmit);

        // Фильтры истории
        document.getElementById('history-search').addEventListener('input', filterHistory);
        document.getElementById('history-user-filter').addEventListener('change', filterHistory);
    }

    // ========== Пользователи ==========
    async function loadUsers() {
        try {
            const response = await fetch(API.users);
            if (!response.ok) throw new Error('Ошибка загрузки пользователей');

            const users = await response.json();
            renderUsers(users);
            populateUserSelect(users);
            populateHistoryUserFilter(users);
        } catch (error) {
            console.error('Ошибка:', error);
            alert('Не удалось загрузить пользователей');
        }
    }

    function renderUsers(users) {
        const tbody = document.querySelector('#users-table tbody');
        tbody.innerHTML = '';

        users.forEach(user => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.email}</td>
                <td>
                    <button class="btn-edit" data-id="${user.id}">Изменить</button>
                    <button class="btn-danger" data-id="${user.id}">Удалить</button>
                </td>
            `;
            tbody.appendChild(tr);
        });

        // Обработчики для кнопок
        document.querySelectorAll('.btn-edit').forEach(btn => {
            btn.addEventListener('click', function() {
                editUser(this.dataset.id);
            });
        });

        document.querySelectorAll('.btn-danger').forEach(btn => {
            btn.addEventListener('click', function() {
                deleteUser(this.dataset.id);
            });
        });
    }

    function populateUserSelect(users) {
        const select = document.getElementById('user-select');
        select.innerHTML = '<option value="">Выберите пользователя</option>';

        users.forEach(user => {
            const option = document.createElement('option');
            option.value = user.id;
            option.textContent = user.username;
            select.appendChild(option);
        });
    }

    function populateHistoryUserFilter(users) {
        const select = document.getElementById('history-user-filter');
        select.innerHTML = '<option value="">Все пользователи</option>';

        users.forEach(user => {
            const option = document.createElement('option');
            option.value = user.id;
            option.textContent = user.username;
            select.appendChild(option);
        });
    }

    function showUserForm() {
        document.getElementById('user-form-container').classList.remove('hidden');
        document.getElementById('user-form').reset();
        document.getElementById('user-id').value = '';
    }

    function hideUserForm() {
        document.getElementById('user-form-container').classList.add('hidden');
    }

    async function handleUserSubmit(e) {
        e.preventDefault();

        const userId = document.getElementById('user-id').value;
        const username = document.getElementById('username').value;
        const email = document.getElementById('email').value;

        const user = { username, email };

        try {
            let response;
            if (userId) {
                // Редактирование существующего пользователя
                response = await fetch(`${API.users}/${userId}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(user)
                });
            } else {
                // Создание нового пользователя
                response = await fetch(API.users, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(user)
                });
            }

            if (!response.ok) throw new Error('Ошибка сохранения пользователя');

            hideUserForm();
            await loadUsers();
        } catch (error) {
            console.error('Ошибка:', error);
            alert('Не удалось сохранить пользователя');
        }
    }

    async function editUser(userId) {
        try {
            const response = await fetch(`${API.users}/${userId}`);
            if (!response.ok) throw new Error('Ошибка загрузки пользователя');

            const user = await response.json();

            document.getElementById('user-id').value = user.id;
            document.getElementById('username').value = user.username;
            document.getElementById('email').value = user.email;

            showUserForm();
        } catch (error) {
            console.error('Ошибка:', error);
            alert('Не удалось загрузить данные пользователя');
        }
    }

    async function deleteUser(userId) {
        if (!confirm('Вы уверены, что хотите удалить этого пользователя?')) return;

        try {
            const response = await fetch(`${API.users}/${userId}`, {
                method: 'DELETE'
            });

            if (!response.ok) throw new Error('Ошибка удаления пользователя');

            await loadUsers();
        } catch (error) {
            console.error('Ошибка:', error);
            alert('Не удалось удалить пользователя');
        }
    }

    // ========== Определение языка ==========
    async function handleDetectionSubmit(e) {
        e.preventDefault();

        const userId = document.getElementById('user-select').value;
        const text = document.getElementById('text-to-detect').value;

        if (!userId || !text) {
            alert('Пожалуйста, выберите пользователя и введите текст');
            return;
        }

        try {
            const response = await fetch(`${API.detection}?text=${encodeURIComponent(text)}&userId=${userId}`);
            if (!response.ok) throw new Error('Ошибка определения языка');

            const language = await response.text();
            document.getElementById('detected-language').textContent = language;

            // Обновляем историю
            await loadHistory();
        } catch (error) {
            console.error('Ошибка:', error);
            alert('Не удалось определить язык текста');
        }
    }

    // ========== История определений ==========
    async function loadHistory() {
        try {
            const response = await fetch(API.history);
            if (!response.ok) throw new Error('Ошибка загрузки истории');

            const history = await response.json();
            renderHistory(history);
        } catch (error) {
            console.error('Ошибка:', error);
            alert('Не удалось загрузить историю определений');
        }
    }

    function renderHistory(history) {
        const tbody = document.querySelector('#history-table tbody');
        tbody.innerHTML = '';

        history.forEach(item => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${item.id}</td>
                <td>${item.user ? item.user.username : 'Неизвестно'}</td>
                <td>${item.text.length > 50 ? item.text.substring(0, 50) + '...' : item.text}</td>
                <td>${item.detectedLanguage}</td>
                <td>${new Date(item.date).toLocaleString()}</td>
                <td>
                    <button class="btn-danger" data-id="${item.id}">Удалить</button>
                </td>
            `;
            tbody.appendChild(tr);
        });

        // Обработчики для кнопок удаления
        document.querySelectorAll('#history-table .btn-danger').forEach(btn => {
            btn.addEventListener('click', function() {
                deleteHistoryItem(this.dataset.id);
            });
        });
    }

    function filterHistory() {
        const searchText = document.getElementById('history-search').value.toLowerCase();
        const userId = document.getElementById('history-user-filter').value;

        const rows = document.querySelectorAll('#history-table tbody tr');

        rows.forEach(row => {
            const text = row.cells[2].textContent.toLowerCase();
            const rowUserId = row.cells[1].dataset.userId || '';

            const matchesSearch = text.includes(searchText);
            const matchesUser = !userId || rowUserId === userId;

            row.style.display = matchesSearch && matchesUser ? '' : 'none';
        });
    }

    async function deleteHistoryItem(historyId) {
        if (!confirm('Вы уверены, что хотите удалить эту запись?')) return;

        try {
            const response = await fetch(`${API.history}/${historyId}`, {
                method: 'DELETE'
            });

            if (!response.ok) throw new Error('Ошибка удаления записи');

            await loadHistory();
        } catch (error) {
            console.error('Ошибка:', error);
            alert('Не удалось удалить запись');
        }
    }

    // ========== Статистика ==========
    async function loadStatistics() {
        try {
            // Общее количество запросов
            const requestsResponse = await fetch(API.stats);
            if (!requestsResponse.ok) throw new Error('Ошибка загрузки статистики запросов');
            const totalRequests = await requestsResponse.json();
            document.getElementById('total-requests').textContent = totalRequests;

            // Количество пользователей
            const usersResponse = await fetch(API.users);
            if (!usersResponse.ok) throw new Error('Ошибка загрузки пользователей');
            const users = await usersResponse.json();
            document.getElementById('total-users').textContent = users.length;

            // Количество определений (используем историю)
            const historyResponse = await fetch(API.history);
            if (!historyResponse.ok) throw new Error('Ошибка загрузки истории');
            const history = await historyResponse.json();
            document.getElementById('total-detections').textContent = history.length;
        } catch (error) {
            console.error('Ошибка:', error);
            alert('Не удалось загрузить статистику');
        }
    }
});