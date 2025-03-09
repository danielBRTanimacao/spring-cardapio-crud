package com.daniel.menu.entity;

import com.daniel.menu.dto.FoodRequestDTO;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

@Table(name = "food")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Food {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Valid

    @NotNull(message = "Title deve conter o nome do alimento!")
    @NotBlank(message = "Title deve conter o nome do alimento!")
    private String title;
    @NotNull(message = "Image deve conter a imagem alimento!")
    @NotBlank(message = "Image deve conter a imagem alimento!")
    private String image;
    @NotNull(message = "Title deve conter o preço do alimento!")
    private Integer price;

    public Food(FoodRequestDTO data) {
        this.title = data.title();
        this.image = data.image();
        this.price = data.price();
    }
}
