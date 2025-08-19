package com.roi.car.services;

import com.roi.car.models.Car;
import com.roi.car.services.CSVUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {

    private final List<Car> cars = new ArrayList<>();
    private final String FILE_NAME = "data/cars.csv";

    @PostConstruct
    public void init() {
        List<Car> loadedCars = CSVUtil.readCarsFromCSV(FILE_NAME);
        if (loadedCars != null) {
            cars.addAll(loadedCars);
        }
    }

    public List<Car> getAllCars() {
        return cars;
    }

    public void addCar(Car car) {
        car.setCarId(getNextId());
        cars.add(car);
        CSVUtil.saveCarsToCSV(cars, FILE_NAME);
    }

    public void updateCar(long id, Car updatedCar) {
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getCarId() == id) {
                updatedCar.setCarId(id);
                cars.set(i, updatedCar);
                CSVUtil.saveCarsToCSV(cars, FILE_NAME);
                break;
            }
        }
    }

    public void deleteCar(long id) {
        cars.removeIf(car -> car.getCarId() == id);
        CSVUtil.saveCarsToCSV(cars, FILE_NAME);
    }

    private long getNextId() {
        if (cars.isEmpty()) return 1;
        return cars.stream().mapToLong(Car::getCarId).max().getAsLong() + 1;
    }
}
