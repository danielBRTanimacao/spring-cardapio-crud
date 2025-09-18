package com.daniel.menu.controller;

import java.util.*;

import com.daniel.menu.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.daniel.menu.dto.*;
import com.daniel.menu.entity.Food;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping
    public Page<Food> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return foodService.getAllFoods(page, size);
    }

    @PostMapping
    public ResponseEntity<Void> saveNewFood(@Valid @ModelAttribute FoodRequestDTO data) {
        foodService.createFood(data);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable Long id) {
        foodService.delFood(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFood(@PathVariable Long id, @Valid @RequestBody FoodUpdateDTO data) {
        Food preFood = new Food();
        preFood.setImage(data.image());
        preFood.setPrice(data.price());
        preFood.setTitle(data.title());
        foodService.updateFood(id, preFood);
        return ResponseEntity.ok().build();
    }

}
