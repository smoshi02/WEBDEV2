const apiUrl = "http://localhost:8080/api";

document.getElementById("loginForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();

    if (!username || !password) {
        showError("Please enter both username and password.");
        return;
    }

    fetch(`${apiUrl}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password })
    })
    .then(res => {
        if (!res.ok) throw new Error("Invalid credentials");
        return res.json();
    })
    .then(data => {
        // Save JWT token in localStorage
        localStorage.setItem("jwtToken", data.token);
        // Redirect to index.html
        window.location.href = "index.html";
    })
    .catch(err => {
        console.error(err);
        showError("Invalid username or password.");
    });
});

function showError(msg) {
    const el = document.getElementById("errorMsg");
    el.innerText = msg;
    el.classList.remove("hidden");
}
