package com.roi.cartoh.dto;

import jakarta.validation.constraints.*;

public class CarDTO {

    private Integer id; // Add ID for editing

    @NotBlank(message = "Make is required")
    private String make;

    @NotBlank(message = "Model is required")
    private String model;

    @Min(value = 1900, message = "Year must be >= 1900")
    @Max(value = 2100, message = "Year must be <= 2100")
    private Integer year;

    @NotBlank(message = "License Plate Number is required")
    private String licensePlateNumber; // Added this field

    @NotBlank(message = "Color is required")
    private String color;

    @NotBlank(message = "Body Type is required")
    private String bodyType;

    @NotBlank(message = "Engine Type is required")
    private String engineType;

    @NotBlank(message = "Transmission is required")
    private String transmission;

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }
}
