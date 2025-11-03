package com.decimo.debweb.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductDTO(
        @NotBlank String name,
        @NotBlank String description,
        @Min(1) int stock,
        @NotBlank String unit,
        @NotNull @Min(1) double price
) {

}