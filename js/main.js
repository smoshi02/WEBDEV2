var apiUrl ='http://localhost:8080/api';
fetchCars();

function fetchCars(){
    fetch (`${apiUrl}/cars`)
    .then (res => res.json())
    .then (cars => {
        console.log(cars)
        const tbody = document.getElementById("carTableBody");
        tbody.innerHTML = "";
        var counter = 0;
        cars.forEach(car => {
            
            tbody.innerHTML += `
            <tr class="text-center">
                <td class="p-2 border">${++counter}</td>
                <td class="p-2 border">${car.make}</td>
                <td class="p-2 border">${car.model}</td>
                <td class="p-2 border">${car.year}</td>
                <td class="p-2 border">${car.licensePlateNumber}</td>
                <td class="p-2 border">${car.color}</td>
                <td class="p-2 border">${car.bodyType}</td>
                <td class="p-2 border">${car.engineType}</td>
                <td class="p-2 border">${car.transmission}</td>
                <td class="p-2 border">
                    <button onclick="deleteCar(${car.id})" class="bg-red-500 color-white px-4 py-1 hover:bg-red-700">Delete</button>
                </td>
            </tr>
            `
        })
    })
    .catch(error => console.error(error));
}

function deleteCar(id) {
    if (confirm('Delete this car?')) {
        fetch(`${apiUrl}/${id}`, { method: 'DELETE' })
            .then(() => fetchCars());
            
    }
}

function openModal(){
    const formModal = document.getElementById("formModal");
    formModal.classList.remove("hidden")
    formModal.querySelector("#modalTitle").innerText="New Car";
}

function saveCar(event){
    event.prevent
}