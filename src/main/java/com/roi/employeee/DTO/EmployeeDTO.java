package com.roi.employeee.DTO;

import jakarta.validation.constraints.*;

public class EmployeeDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 5, max = 100, message = "Name should be between 5 and 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
