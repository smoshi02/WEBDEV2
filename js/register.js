const apiUrl = "http://localhost:8080/api";

document.getElementById("registerForm").addEventListener("submit", function (e) {
  e.preventDefault();

  const username = document.getElementById("registerUsername").value.trim();
  const password = document.getElementById("registerPassword").value.trim();
  const confirmPassword = document.getElementById("registerConfirmPassword").value.trim();

  // Clear previous error messages
  clearErrors();

  let hasError = false;

  // Field validations
  if (!username) {
    showFieldError("usernameError", "Please fill in the username.");
    hasError = true;
  }

  if (!password) {
    showFieldError("passwordError", "Please fill in the password.");
    hasError = true;
  }

  if (!confirmPassword) {
    showFieldError("confirmPasswordError", "Please confirm your password.");
    hasError = true;
  }

  if (password && confirmPassword && password !== confirmPassword) {
    showFieldError("confirmPasswordError", "Passwords do not match.");
    hasError = true;
  }

  if (hasError) return;

  // Send registration data
  fetch(`${apiUrl}/auth/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, password })
  })
    .then(res => {
      if (!res.ok) {
        return res.json().then(data => {
          throw new Error(data.message || "Registration failed");
        });
      }
      return res.json();
    })
    .then(() => {
      // Redirect to login page without alerts
      window.location.href = "login.html";
    })
    .catch(err => {
      showFieldError("confirmPasswordError", err.message);
    });
});

function showFieldError(id, message) {
  const el = document.getElementById(id);
  el.innerText = message;
  el.classList.remove("hidden");
}

function clearErrors() {
  ["usernameError", "passwordError", "confirmPasswordError"].forEach(id => {
    const el = document.getElementById(id);
    el.innerText = "";
    el.classList.add("hidden");
  });
}
