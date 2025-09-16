package com.roi.employeee.controller;

import com.roi.employeee.DTO.EmployeeDTO;
import com.roi.employeee.exception.ResourceNotFoundException;
import com.roi.employeee.model.Employee;
import com.roi.employeee.repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/")
    public String index(@RequestParam(required = false) String keyword, Model model) {
        List<Employee> employees;
        if (keyword != null && !keyword.isEmpty()) {
            employees = employeeRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
        } else {
            employees = employeeRepository.findAll();
        }
        model.addAttribute("employees", employees);
        model.addAttribute("keyword", keyword);
        return "index";
    }

    @GetMapping("/delete")
    public String deleteEmployee(@RequestParam int id) {
        employeeRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/new")
    public String add(Model model) {
        model.addAttribute("employee", new EmployeeDTO());
        return "add_employee";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("employee") @Valid EmployeeDTO employeeDTO,
                       BindingResult bindingResult,
                       Model model) {

        if (employeeRepository.existsByEmail(employeeDTO.getEmail())) {
            bindingResult.rejectValue("email", "error.email", "Email already exists");
        }

        if (bindingResult.hasErrors()) {
            return "add_employee";
        }

        Employee newEmployee = new Employee();
        newEmployee.setName(employeeDTO.getName());
        newEmployee.setEmail(employeeDTO.getEmail());

        employeeRepository.save(newEmployee);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam int id, Model model) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee != null) {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setId(employee.getId());
            employeeDTO.setName(employee.getName());
            employeeDTO.setEmail(employee.getEmail());

            model.addAttribute("employee", employeeDTO);
            model.addAttribute("employeeId", id);
            return "edit_employee";
        }

        model.addAttribute("message", "This ID isn't available. Sorry about that.");
        return "error/error";
    }

    @PostMapping("/update")
    public String update(@RequestParam int id,
                         @ModelAttribute("employee") @Valid EmployeeDTO employeeDTO,
                         BindingResult bindingResult,
                         Model model) {

        if (employeeRepository.existsByEmail(employeeDTO.getEmail()) && !employeeRepository.findById(id).get().getEmail().equals(employeeDTO.getEmail())) {
            bindingResult.rejectValue("email", "error.email", "Email already exists");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("employeeId", id);
            return "edit_employee";
        }

        Employee existingEmployee = employeeRepository.findById(id).orElse(null);
        if (existingEmployee == null) {
            return "redirect:/";
        }

        existingEmployee.setName(employeeDTO.getName());
        existingEmployee.setEmail(employeeDTO.getEmail());

        employeeRepository.save(existingEmployee);
        return "redirect:/";
    }

}
