package com.daniel.menu.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import com.daniel.menu.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.daniel.menu.dto.*;
import com.daniel.menu.entity.Food;
import com.daniel.menu.repository.FoodRepository;

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
        return foodService.delFood(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFood(@PathVariable Long id, @Valid @RequestBody FoodUpdateDTO data) {
        Food preFood = new Food();
        preFood.setImage(data.image());
        preFood.setPrice(data.price());
        preFood.setTitle(data.title());

        return foodService.updateFood(id, preFood);
    }

}
