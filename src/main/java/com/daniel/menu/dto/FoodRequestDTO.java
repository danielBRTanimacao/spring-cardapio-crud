package com.daniel.menu.dto;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public record FoodRequestDTO(
    @NotBlank
    String title,

    @NotNull
    MultipartFile image,

    @NotNull
    @DecimalMin("1.00")
    @DecimalMax("9999.00")
    BigDecimal price
) {}
