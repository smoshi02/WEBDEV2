const apiUrl = 'http://localhost:8080/api';

document.addEventListener("DOMContentLoaded", function() {
    fetchCars();

    document.getElementById("carForm").addEventListener("submit", saveCar);

    document.getElementById("formSection").addEventListener("click", function(e) {
        if (e.target.id === "formSection") closeModal();
    });
});

function fetchCars() {
    fetch(`${apiUrl}/cars`)
        .then(res => {
            if (!res.ok) throw new Error("Failed to fetch cars");
            return res.json();
        })
        .then(cars => {
            const tbody = document.getElementById("carTableBody");
            tbody.innerHTML = ""; 

            cars.forEach((car, index) => {
                tbody.innerHTML += `
                    <tr class="hover:bg-green-50 transition">
                        <td class="px-4 py-3 text-center">${index + 1}</td>
                        <td class="px-4 py-3 text-center">${car.make}</td>
                        <td class="px-4 py-3 text-center">${car.model}</td>
                        <td class="px-4 py-3 text-center">${car.year}</td>
                        <td class="px-4 py-3 text-center">${car.licensePlateNumber}</td>
                        <td class="px-4 py-3 text-center">${car.color}</td>
                        <td class="px-4 py-3 text-center">${car.bodyType}</td>
                        <td class="px-4 py-3 text-center">${car.engineType}</td>
                        <td class="px-4 py-3 text-center">${car.transmission}</td>
                        <td class="px-4 py-3 text-center">
                            <button onclick="editCar(${car.id})"
                                class="bg-yellow-500 hover:bg-yellow-600 text-white px-3 py-1.5 rounded-md mr-2 transition">
                                Edit
                            </button>
                            <button onclick="deleteCar(${car.id})"
                                class="bg-red-500 hover:bg-red-600 text-white px-3 py-1.5 rounded-md transition">
                                Delete
                            </button>
                        </td>
                    </tr>
                `;
            });
        })
        .catch(err => console.error("Error fetching cars:", err));
}

function openModal() {
    document.getElementById("formSection").classList.remove("hidden");
    document.getElementById("modalTitle").innerText = "Add New Car";
    document.getElementById("carForm").reset();
    document.getElementById("carId").value = "";
}

function closeModal() {
    document.getElementById("formSection").classList.add("hidden");
}


function saveCar(event) {
    event.preventDefault();

    const car = {
        id: document.getElementById("carId").value,
        make: document.getElementById("make").value,
        model: document.getElementById("model").value,
        year: document.getElementById("year").value,
        licensePlateNumber: document.getElementById("licensePlateNumber").value,
        color: document.getElementById("color").value,
        bodyType: document.getElementById("bodyType").value,
        engineType: document.getElementById("engineType").value,
        transmission: document.getElementById("transmission").value
    };

    const method = car.id ? "PUT" : "POST";
    const url = car.id ? `${apiUrl}/cars/${car.id}` : `${apiUrl}/cars`;

    fetch(url, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(car)
    })
    .then(res => {
        if (!res.ok) throw new Error("Failed to save car");
        return res.json();
    })
    .then(() => {
        closeModal();
        fetchCars();
    })
    .catch(err => console.error("Error saving car:", err));
}


function editCar(id) {
    fetch(`${apiUrl}/cars/${id}`)
        .then(res => {
            if (!res.ok) throw new Error("Failed to fetch car");
            return res.json();
        })
        .then(car => {
            openModal();
            document.getElementById("modalTitle").innerText = "Edit Car";
            document.getElementById("carId").value = car.id;
            document.getElementById("make").value = car.make;
            document.getElementById("model").value = car.model;
            document.getElementById("year").value = car.year;
            document.getElementById("licensePlateNumber").value = car.licensePlateNumber;
            document.getElementById("color").value = car.color;
            document.getElementById("bodyType").value = car.bodyType;
            document.getElementById("engineType").value = car.engineType;
            document.getElementById("transmission").value = car.transmission;
        })
        .catch(err => console.error("Error editing car:", err));
}


function deleteCar(id) {
    fetch(`${apiUrl}/cars/${id}`, { method: "DELETE" })
        .then(res => {
            if (!res.ok) throw new Error("Failed to delete car");
            fetchCars();
        })
        .catch(err => console.error(err));
}
