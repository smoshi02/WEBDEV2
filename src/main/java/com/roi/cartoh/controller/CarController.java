package com.roi.cartoh.controller;

import com.roi.cartoh.model.Car;
import com.roi.cartoh.repository.CarRepository;
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
    public String index(Model model) {
        List<Car> cars = carRepository.findAll();
        model.addAttribute("cars", cars);
        return "car"; // car.html
    }

    @GetMapping("/delete")
    public String deleteCar(@RequestParam int id) {
        carRepository.deleteById(id);
        return "redirect:/"; // ✅ redirect back to list
    }

    @GetMapping("/new")
    public String add(Model model) {
        model.addAttribute("car", new Car());
        model.addAttribute("types", new String[]{"Gasoline", "Diesel", "Electric", "Hybrid"});
        model.addAttribute("sizes", new String[]{"Automatic", "Manual"});
        return "add_car"; // add_car.html
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("car") Car car,
                       BindingResult bindingResult,
                       Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", new String[]{"Gasoline", "Diesel", "Electric", "Hybrid"});
            model.addAttribute("sizes", new String[]{"Automatic", "Manual"});
            return "add_car";
        }
        carRepository.save(car);
        return "redirect:/"; // ✅ redirect after save
    }

    @GetMapping("/edit")
    public String edit(@RequestParam int id, Model model) {
        Car c = carRepository.findById(id).orElse(null);
        if (c != null) {
            model.addAttribute("car", c);
            model.addAttribute("types", new String[]{"Gasoline", "Diesel", "Electric", "Hybrid"});
            model.addAttribute("sizes", new String[]{"Automatic", "Manual"});
            return "edit_car"; // edit_car.html
        }
        return "redirect:/"; // ✅ if not found
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("car") Car car,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", new String[]{"Gasoline", "Diesel", "Electric", "Hybrid"});
            model.addAttribute("sizes", new String[]{"Automatic", "Manual"});
            return "edit_car";
        }
        carRepository.save(car);
        return "redirect:/"; // ✅ redirect after update
    }


}

