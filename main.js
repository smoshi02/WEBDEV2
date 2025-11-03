const apiBase = "http://localhost:8080/api/products";

document.addEventListener("DOMContentLoaded", fetchProducts);

function fetchProducts() {
  fetch(apiBase)
    .then(res => res.json())
    .then(products => {
      const body = document.getElementById("productTableBody");
      body.innerHTML = "";
      let counter = 0;
      products.forEach(p => {
        body.innerHTML += `
          <tr class="text-center">
            <td class="border p-2">${++counter}</td>
            <td class="border p-2">${p.name}</td>
            <td class="border p-2">${p.description}</td>
            <td class="border p-2">${p.stock}</td>
            <td class="border p-2">${p.unit}</td>
            <td class="border p-2">${p.price}</td>
            <td class="border p-2">
              <button onclick="openEditModal(${p.id}, '${p.name}', '${p.description}', ${p.stock}, '${p.unit}', ${p.price})"
                class="bg-yellow-500 text-white px-3 py-1 rounded hover:bg-yellow-600">Edit</button>
              <button onclick="deleteProduct(${p.id})"
                class="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600">Delete</button>
            </td>
          </tr>`;
      });
    })
    .catch(err => console.error(err));
}

function openCreateModal() {
  document.getElementById("productForm").reset();
  clearErrors();
  document.getElementById("productId").value = "";
  document.getElementById("modalTitle").innerText = "Add Product";
  document.getElementById("productModal").classList.remove("hidden");
}

function openEditModal(id, name, description, stock, unit, price) {
  clearErrors();
  document.getElementById("productId").value = id;
  document.getElementById("productName").value = name;
  document.getElementById("productDescription").value = description;
  document.getElementById("productStock").value = stock;
  document.getElementById("productUnit").value = unit;
  document.getElementById("productPrice").value = price;
  document.getElementById("modalTitle").innerText = "Edit Product";
  document.getElementById("productModal").classList.remove("hidden");
}

function closeModal() {
  document.getElementById("productModal").classList.add("hidden");
}

function clearErrors() {
  document.querySelectorAll(".error-text").forEach(e => e.remove());
  document.querySelectorAll(".border-red-500").forEach(i => {
    i.classList.remove("border-red-500");
  });
}

function showError(inputId, message) {
  const input = document.getElementById(inputId);
  input.classList.add("border-red-500");

  let existingError = input.parentNode.querySelector(".error-text");
  if (existingError) existingError.remove();

  const error = document.createElement("p");
  error.className = "text-red-500 text-sm mt-1 error-text";
  error.innerText = message;
  input.parentNode.appendChild(error);
}

function saveProduct(e) {
  e.preventDefault();
  clearErrors();

  const id = document.getElementById("productId").value;
  const name = document.getElementById("productName").value.trim();
  const description = document.getElementById("productDescription").value.trim();
  const stock = parseInt(document.getElementById("productStock").value);
  const unit = document.getElementById("productUnit").value.trim();
  const price = parseFloat(document.getElementById("productPrice").value);

  let isValid = true;

  if (!name) {
    showError("productName", "Product name is required.");
    isValid = false;
  }
  if (!description) {
    showError("productDescription", "Description is required.");
    isValid = false;
  }
  if (isNaN(stock) || stock < 1) {
    showError("productStock", "Stock must be at least 1.");
    isValid = false;
  }
  if (!unit) {
    showError("productUnit", "Unit is required.");
    isValid = false;
  }
  if (isNaN(price) || price < 1) {
    showError("productPrice", "Price must be at least 1.");
    isValid = false;
  }

  if (!isValid) return;

  const product = { name, description, stock, unit, price };
  const method = id ? "PUT" : "POST";
  const url = id ? `${apiBase}/${id}` : apiBase;

  fetch(url, {
    method,
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(product)
  })
    .then(res => {
      if (!res.ok) return res.json().then(err => { throw err; });
      return res.json();
    })
    .then(() => {
      closeModal();
      fetchProducts();
    })
    .catch(err => alert("Error: " + JSON.stringify(err)));
}

function deleteProduct(id) {
  if (!confirm("Delete this product?")) return;
  fetch(`${apiBase}/${id}`, { method: "DELETE" })
    .then(() => fetchProducts())
    .catch(err => console.error(err));
}