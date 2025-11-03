const apiBase = "http://localhost:8080/api/products";

document.addEventListener("DOMContentLoaded", fetchProducts);

async function fetchProducts() {
    const res = await fetch(apiBase);
    const products = await res.json();
    const tbody = document.getElementById("productTableBody");
    tbody.innerHTML = "";
    let counter = 0;
    products.forEach(p => {
        tbody.innerHTML += `
        <tr class="text-center border-b">
            <td class="p-2">${++counter}</td>
            <td class="p-2">${p.name}</td>
            <td class="p-2">${p.description}</td>
            <td class="p-2">${p.stock}</td>
            <td class="p-2">${p.unit}</td>
            <td class="p-2">${p.price.toFixed(2)}</td>
            <td class="p-2">
                <button onclick="openEditModal(${p.id}, '${p.name}', '${p.description}', ${p.stock}, '${p.unit}', ${p.price})" class="bg-yellow-500 text-white px-3 py-1 rounded hover:bg-yellow-600">Edit</button>
                <button onclick="deleteProduct(${p.id})" class="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600">Delete</button>
            </td>
        </tr>`;
    });
}

function openCreateModal() {
    document.getElementById("productForm").reset();
    document.getElementById("productId").value = "";
    document.getElementById("modalTitle").innerText = "Add Product";
    document.getElementById("errorMsg").classList.add("hidden");
    document.getElementById("productModal").classList.remove("hidden");
}

function openEditModal(id, name, description, stock, unit, price) {
    document.getElementById("productId").value = id;
    document.getElementById("productName").value = name;
    document.getElementById("productDescription").value = description;
    document.getElementById("productStock").value = stock;
    document.getElementById("productUnit").value = unit;
    document.getElementById("productPrice").value = price;
    document.getElementById("modalTitle").innerText = "Edit Product";
    document.getElementById("errorMsg").classList.add("hidden");
    document.getElementById("productModal").classList.remove("hidden");
}

function closeModal() {
    document.getElementById("productModal").classList.add("hidden");
}

async function saveProduct(e) {
    e.preventDefault();
    const id = document.getElementById("productId").value;
    const name = document.getElementById("productName").value.trim();
    const description = document.getElementById("productDescription").value.trim();
    const stock = parseInt(document.getElementById("productStock").value);
    const unit = document.getElementById("productUnit").value.trim();
    const price = parseFloat(document.getElementById("productPrice").value);
    const errorMsg = document.getElementById("errorMsg");

    if (!name || !description || !unit || isNaN(stock) || isNaN(price) || stock < 1 || price < 1) {
        errorMsg.textContent = "Please fill in all fields correctly.";
        errorMsg.classList.remove("hidden");
        return;
    }

    const method = id ? "PUT" : "POST";
    const url = id ? `${apiBase}/${id}` : apiBase;
    const product = { name, description, stock, unit, price };

    const res = await fetch(url, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(product)
    });

    if (res.ok) {
        closeModal();
        fetchProducts();
    } else {
        const err = await res.json();
        errorMsg.textContent = Object.values(err).join(", ");
        errorMsg.classList.remove("hidden");
    }
}

async function deleteProduct(id) {
    if (!confirm("Are you sure you want to delete this product?")) return;
    await fetch(`${apiBase}/${id}`, { method: "DELETE" });
    fetchProducts();
}
