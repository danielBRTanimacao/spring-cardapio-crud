package com.daniel.menu.entity;

import com.daniel.menu.dto.FoodRequestDTO;

import jakarta.persistence.*;

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
    private String title;
    private String image;
    private Integer price;

    public Food(FoodRequestDTO data) {
        this.title = data.title();
        this.image = data.image();
        this.price = data.price();
    }
}
