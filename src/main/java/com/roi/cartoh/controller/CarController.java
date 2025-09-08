package com.roi.cartoh.controller;

import com.roi.cartoh.dto.CarDTO;
import com.roi.cartoh.exception.ResourceNotFoundException;
import com.roi.cartoh.model.Car;
import com.roi.cartoh.repository.CarRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CarController {

    private final CarRepository carRepository;

    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping("/")
    public String index(@RequestParam(required = false) String keyword, Model model) {
        List<Car> cars;
        if (keyword != null && !keyword.isEmpty()) {
            cars = carRepository.findByMakeContainingIgnoreCaseOrLicensePlateNumberContainingIgnoreCase(keyword, keyword);
        } else {
            cars = carRepository.findAll();
        }
        model.addAttribute("cars", cars);
        model.addAttribute("keyword", keyword); // keep search box filled
        return "car"; // car.html
    }

    @GetMapping("/delete")
    public String deleteCar(@RequestParam int id) {
        carRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/new")
    public String add(Model model) {
        model.addAttribute("car", new CarDTO());
        model.addAttribute("types", new String[]{"Gasoline", "Diesel", "Electric", "Hybrid"});
        model.addAttribute("sizes", new String[]{"Automatic", "Manual"});
        return "add_car"; // add_car.html
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("car") @Valid CarDTO carDTO,
                       BindingResult bindingResult,
                       Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", new String[]{"Gasoline", "Diesel", "Electric", "Hybrid"});
            model.addAttribute("sizes", new String[]{"Automatic", "Manual"});
            return "add_car"; // back to form if validation fails
        }

        // Map DTO to entity
        Car newCar = new Car();
        newCar.setMake(carDTO.getMake());
        newCar.setModel(carDTO.getModel());
        newCar.setYear(carDTO.getYear());
        newCar.setLicensePlateNumber(carDTO.getLicensePlateNumber()); // Added
        newCar.setColor(carDTO.getColor());
        newCar.setBodyType(carDTO.getBodyType());
        newCar.setEngineType(carDTO.getEngineType());
        newCar.setTransmission(carDTO.getTransmission());

        carRepository.save(newCar);
        return "redirect:/"; // success, redirect to list
    }

    @GetMapping("/edit")
    public String edit(@RequestParam int id, Model model) {
        Car car = carRepository.findById(id).orElse(null);
        if (car != null) {
            CarDTO carDTO = new CarDTO();
            carDTO.setId(car.getId());
            carDTO.setMake(car.getMake());
            carDTO.setModel(car.getModel());
            carDTO.setYear(car.getYear());
            carDTO.setLicensePlateNumber(car.getLicensePlateNumber());
            carDTO.setColor(car.getColor());
            carDTO.setBodyType(car.getBodyType());
            carDTO.setEngineType(car.getEngineType());
            carDTO.setTransmission(car.getTransmission());

            model.addAttribute("car", carDTO);
            model.addAttribute("types", new String[]{"Gasoline", "Diesel", "Electric", "Hybrid"});
            model.addAttribute("sizes", new String[]{"Automatic", "Manual"});
            model.addAttribute("carId", id);
            return "edit_car"; // edit_car.html
        }

        model.addAttribute("message", "Car with ID " + id + " not found.");
        return "error/error";
    }


    @PostMapping("/update")
    public String update(@RequestParam int id,
                         @ModelAttribute("car") @Valid CarDTO carDTO,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", new String[]{"Gasoline", "Diesel", "Electric", "Hybrid"});
            model.addAttribute("sizes", new String[]{"Automatic", "Manual"});
            model.addAttribute("carId", id);
            return "edit_car"; // back to form if errors
        }

        Car existingCar = carRepository.findById(id).orElse(null);
        if (existingCar == null) {
            return "redirect:/"; // no car found, back to list
        }

        // Update fields
        existingCar.setMake(carDTO.getMake());
        existingCar.setModel(carDTO.getModel());
        existingCar.setYear(carDTO.getYear());
        existingCar.setLicensePlateNumber(carDTO.getLicensePlateNumber()); // Added
        existingCar.setColor(carDTO.getColor());
        existingCar.setBodyType(carDTO.getBodyType());
        existingCar.setEngineType(carDTO.getEngineType());
        existingCar.setTransmission(carDTO.getTransmission());

        carRepository.save(existingCar);
        return "redirect:/"; // success
    }

    @GetMapping("/view")
    public String view(@RequestParam int id, Model model) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car", (long) id));
        model.addAttribute("car", car);
        return "view";
    }
}
