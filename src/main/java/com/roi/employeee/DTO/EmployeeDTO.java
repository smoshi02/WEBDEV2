package com.roi.employeee.DTO;

import jakarta.validation.constraints.*;

public class EmployeeDTO {

    private Long id;

    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    private String name;

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
