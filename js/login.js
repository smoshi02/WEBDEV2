const apiUrl = "http://localhost:8080/api";

document.getElementById("loginForm").addEventListener("submit", function (e) {
  e.preventDefault();

  const username = document.getElementById("loginUsername").value.trim();
  const password = document.getElementById("loginPassword").value.trim();

  // Reset all error messages
  clearErrors();

  let hasError = false;

  // Username validation
  if (!username) {
    showFieldError("usernameError", "Please fill in the username.");
    hasError = true;
  }

  // Password validation
  if (!password) {
    showFieldError("passwordError", "Please fill in the password.");
    hasError = true;
  }

  if (hasError) return;

  // Proceed with fetch if validation passes
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
      localStorage.setItem("jwtToken", data.token);
      window.location.href = "index.html";
    })
    .catch(err => {
      showFieldError("passwordError", "Invalid username or password.");
      console.error(err);
    });
});

function showFieldError(id, message) {
  const el = document.getElementById(id);
  el.innerText = message;
  el.classList.remove("hidden");
}

function clearErrors() {
  document.getElementById("usernameError").classList.add("hidden");
  document.getElementById("passwordError").classList.add("hidden");
  document.getElementById("usernameError").innerText = "";
  document.getElementById("passwordError").innerText = "";
}
