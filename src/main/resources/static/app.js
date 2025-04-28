document.addEventListener("DOMContentLoaded", () => {
    const users = [];
    const history = [];

    const userFormContainer = document.getElementById("user-form-container");
    const userList = document.getElementById("users-list");
    const userSelect = document.getElementById("user-select");
    const detectBtn = document.getElementById("detect-btn");
    const detectionResult = document.getElementById("detected-language");
    const historyList = document.getElementById("history-list");
    const totalRequests = document.getElementById("total-requests");
    const totalUsers = document.getElementById("total-users");
    const totalDetections = document.getElementById("total-detections");

    // Navigation
    document.querySelectorAll('.main-nav a').forEach(link => {
        link.addEventListener('click', (e) => {
            e.preventDefault();
            const target = e.target.getAttribute('href').substring(1);
            document.querySelectorAll('.section').forEach(section => {
                section.classList.remove('active');
            });
            document.getElementById(target).classList.add('active');
        });
    });

    // Add User
    document.getElementById("add-user-btn").addEventListener("click", () => {
        userFormContainer.style.display = "block";
        document.getElementById("user-form").reset();
    });

    // Cancel User Form
    document.getElementById("cancel-user-btn").addEventListener("click", () => {
        userFormContainer.style.display = "none";
    });

    // Save User
    document.getElementById("user-form").addEventListener("submit", (e) => {
        e.preventDefault();
        const id = Date.now();
        const username = document.getElementById("username").value;
        const email = document.getElementById("email").value;

        users.push({ id, username, email });
        addUserToList({ id, username, email });
        updateUserSelect();
        userFormContainer.style.display = "none";
        updateStatistics();
    });

    // Add user to list
    function addUserToList({ id, username, email }) {
        const row = document.createElement("div");
        row.classList.add("grid-row");
        row.innerHTML = `
            <div class="grid-cell" data-label="ID">${id}</div>
            <div class="grid-cell" data-label="Username">${username}</div>
            <div class="grid-cell" data-label="Email">${email}</div>
            <div class="grid-cell" data-label="Actions">
                <button class="btn-danger" onclick="deleteUser(${id})">Delete</button>
            </div>
        `;
        userList.appendChild(row);
    }

    // Delete User
    window.deleteUser = function (id) {
        const userIndex = users.findIndex(user => user.id === id);
        if (userIndex > -1) {
            users.splice(userIndex, 1);
            userList.innerHTML = ''; // Clear the list
            users.forEach(addUserToList); // Re-add users to the list
            updateUserSelect();
            updateStatistics();
        }
    };

    // Update User Select Dropdown
    function updateUserSelect() {
        userSelect.innerHTML = '<option value="">-- Select User --</option>';
        users.forEach(user => {
            const option = document.createElement("option");
            option.value = user.id;
            option.textContent = user.username;
            userSelect.appendChild(option);
        });
    }

    // Detect Language
    detectBtn.addEventListener("click", () => {
        const selectedUserId = userSelect.value;
        const textToDetect = document.getElementById("text-to-detect").value;
        if (selectedUserId && textToDetect) {
            const detectedLanguage = detectLanguage(textToDetect); // Mock function
            detectionResult.textContent = detectedLanguage;
            addToHistory(selectedUserId, textToDetect, detectedLanguage);
            updateStatistics();
        }
    });

    // Add to History
    function addToHistory(userId, text, language) {
        const timestamp = new Date().toLocaleString();
        history.push({ userId, text, language, date: timestamp });
        updateHistoryList();
    }

    // Update History List
    function updateHistoryList() {
        historyList.innerHTML = '';
        history.forEach(({ userId, text, language, date }) => {
            const row = document.createElement("div");
            row.classList.add("grid-row");
            const user = users.find(u => u.id == userId);
            row.innerHTML = `
                <div class="grid-cell" data-label="ID">${historyList.children.length + 1}</div>
                <div class="grid-cell" data-label="User">${user ? user.username : 'Unknown'}</div>
                <div class="grid-cell" data-label="Text">${text}</div>
                <div class="grid-cell" data-label="Language">${language}</div>
                <div class="grid-cell" data-label="Date">${date}</div>
                <div class="grid-cell" data-label="Actions">
                    <button class="btn-danger" onclick="deleteHistory(${historyList.children.length + 1})">Delete</button>
                </div>
            `;
            historyList.appendChild(row);
        });
    }

    // Delete History Entry
    window.deleteHistory = function (index) {
        history.splice(index - 1, 1);
        updateHistoryList();
    };

    // Update Statistics
    function updateStatistics() {
        totalRequests.textContent = history.length;
        totalUsers.textContent = users.length;
        totalDetections.textContent = history.length; // Assuming each history entry is a detection
    }

    // Mock Language Detection Function
    function detectLanguage(text) {
        // Implement actual language detection logic here
        return "English"; // Example return value
    }
});