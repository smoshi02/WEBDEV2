package com.roi.cartoh.controller.api;

import com.roi.cartoh.dto.CarDTO;
import com.roi.cartoh.model.Car;
import com.roi.cartoh.service.CarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    private final CarService carService;

    public RestController(CarService carService) {
        this.carService = carService;
    }


    @GetMapping("/cars")
    public List<Car> getCars() {
        return carService.findAll();
    }


    @GetMapping("/cars/{id}")
    public Car getCarById(@PathVariable Integer id) {

        return carService.findById(id);
    }


    @PostMapping("/cars")
    public Car newCar(@Valid @RequestBody CarDTO car) {
        return carService.save(car);
    }


    @PutMapping("/cars/{id}")
    public Car updateCar(@PathVariable Integer id, @Valid @RequestBody CarDTO car){
        Car updateCar = carService.findById(id);
        if (updateCar == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with ID "+ id + " not found.");
        }
        return carService.updateCar(id, car);
    }


    @DeleteMapping("/cars/{id}")
    public void deleteCar(@PathVariable Integer id) {
        if(carService.findById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car with ID "+ id + " not found.");
        }
        carService.deleteCar(id);
    }
}
