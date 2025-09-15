package com.daniel.menu.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public record FoodUpdateDTO(
        String title,
        String image,
        @DecimalMin("1.00")
        @DecimalMax("9999.0")
        BigDecimal price
) {
}
