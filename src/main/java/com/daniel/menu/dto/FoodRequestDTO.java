package com.daniel.menu.dto;

import jakarta.validation.constraints.*;

public record FoodRequestDTO(
    @NotNull(message = "title deve conter o nome do alimento!")
    @NotBlank(message = "title deve conter o nome do alimento!")
    String title,

    @NotNull(message = "image deve conter a imagem alimento!")
    @NotBlank(message = "image deve conter a imagem alimento!")
    String image,

    @NotNull(message = "price deve conter o preço do alimento!")
    Integer price
) {}
