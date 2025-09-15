package com.daniel.menu.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record FoodRequestDTO(
    @NotBlank
    String title,

    @NotBlank
    String image,

    @NotNull
    @DecimalMin("1.00")
    @DecimalMax("9999.0")
    BigDecimal price
) {}
