package com.roi.cartoh.controller;

import com.roi.cartoh.dto.CarDTO;
import com.roi.cartoh.model.Car;
import com.roi.cartoh.service.CarService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    private final CarService carService;

    public RestController(CarService carService) {
        this.carService = carService;
    }

    // GET all cars
    @GetMapping("/cars")
    public List<Car> getCars() {
        return carService.findAll();
    }

    // GET car by ID
    @GetMapping("/cars/{id}")
    public Car getCarById(@PathVariable Integer id) {
        return carService.findById(id);
    }

    // POST new car
    @PostMapping("/cars")
    public Car newCar(@Valid @RequestBody CarDTO car) {
        return carService.save(car);
    }

    // PUT update existing car
    @PutMapping("/cars/{id}")
    public Car updateCar(@PathVariable Integer id, @Valid @RequestBody CarDTO car) {
        return carService.updateCar(id, car);
    }

    // DELETE car by ID
    @DeleteMapping("/cars/{id}")
    public void deleteCar(@PathVariable Integer id) {
        carService.deleteCar(id);
    }
}
