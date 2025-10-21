const apiUrl = "http://localhost:8080/api";

document.getElementById("registerForm").addEventListener("submit", function(e) {
  e.preventDefault();

  const username = document.getElementById("registerUsername").value.trim();
  const password = document.getElementById("registerPassword").value;
  const confirmPassword = document.getElementById("registerConfirmPassword").value;
  const errorMsgEl = document.getElementById("registerErrorMsg");

  errorMsgEl.classList.add("hidden");
  errorMsgEl.innerText = "";

  if (!username || !password || !confirmPassword) {
    errorMsgEl.innerText = "All fields are required.";
    errorMsgEl.classList.remove("hidden");
    return;
  }

  if (password !== confirmPassword) {
    errorMsgEl.innerText = "Passwords do not match.";
    errorMsgEl.classList.remove("hidden");
    return;
  }

  fetch(`${apiUrl}/auth/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, password }),
  })
  .then(res => {
    if (!res.ok) return res.json().then(data => {
      throw new Error(data.message || "Registration failed");
    });
    return res.json();
  })
  .then(data => {
    alert("Registration successful! You can now log in.");
    this.reset();
    // Optionally redirect to login page
    window.location.href = "login.html"; // or your login page filename
  })
  .catch(err => {
    errorMsgEl.innerText = err.message;
    errorMsgEl.classList.remove("hidden");
  });
});
