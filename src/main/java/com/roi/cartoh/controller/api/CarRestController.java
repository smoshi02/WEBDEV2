package com.roi.cartoh.controller.api;

import com.roi.cartoh.dto.CarDTO;
import com.roi.cartoh.model.Car;
import com.roi.cartoh.service.CarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin(origins = "*") // allow requests from your frontend
@RestController
@RequestMapping("/api")
public class CarRestController {

    private final CarService carService;

    public CarRestController(CarService carService) {
        this.carService = carService;
    }

    // 🔹 GET all cars
    @GetMapping("/cars")
    public List<Car> getCars() {
        return carService.findAll();
    }

    // 🔹 GET a single car by ID (needed for your edit form)
    @GetMapping("/cars/{id}")
    public Car getCarById(@PathVariable Integer id) {
        Car car = carService.findById(id);
        if (car == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with ID " + id + " not found.");
        }
        return car;
    }

    // 🔹 Add a new car
    @PostMapping("/cars")
    public Car newCar(@Valid @RequestBody CarDTO car) {
        return carService.save(car);
    }

    // 🔹 Update existing car
    @PutMapping("/cars/{id}")
    public Car updateCar(@PathVariable Integer id, @Valid @RequestBody CarDTO carDetails) {
        Car existingCar = carService.findById(id);
        if (existingCar == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with ID " + id + " not found.");
        }
        return carService.updateCar(existingCar, carDetails);
    }

    // 🔹 Delete car
    @DeleteMapping("/cars/{id}")
    public void deleteCar(@PathVariable Integer id) {
        if (carService.findById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with ID " + id + " not found.");
        }
        carService.deleteCar(id);
    }
}
