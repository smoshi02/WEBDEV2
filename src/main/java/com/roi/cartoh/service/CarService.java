package com.roi.cartoh.service;

import com.roi.cartoh.dto.CarDTO;
import com.roi.cartoh.exception.ResourceNotFoundException;
import com.roi.cartoh.model.Car;
import com.roi.cartoh.repository.CarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    // Get all cars
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    // Get car by ID
    public Car findById(Integer id) {
        return carRepository.findById(id).orElse(null);

    }

    // Save new car
    public Car save(CarDTO carDTO) {
        Car newCar = new Car();
        newCar.setMake(carDTO.getMake());
        newCar.setModel(carDTO.getModel());
        newCar.setYear(carDTO.getYear());
        newCar.setLicensePlateNumber(carDTO.getLicensePlateNumber());
        newCar.setColor(carDTO.getColor());
        newCar.setBodyType(carDTO.getBodyType());
        newCar.setEngineType(carDTO.getEngineType());
        newCar.setTransmission(carDTO.getTransmission());
        return carRepository.save(newCar);
    }

    // Update existing car
    public Car updateCar(Car car, CarDTO carDTO) {

        car.setMake(carDTO.getMake());
        car.setModel(carDTO.getModel());
        car.setYear(carDTO.getYear());
        car.setLicensePlateNumber(carDTO.getLicensePlateNumber());
        car.setColor(carDTO.getColor());
        car.setBodyType(carDTO.getBodyType());
        car.setEngineType(carDTO.getEngineType());
        car.setTransmission(carDTO.getTransmission());
        return carRepository.save(car);
    }


    // Delete car
    public void deleteCar(Integer id) {
        carRepository.deleteById(id);
    }
}
