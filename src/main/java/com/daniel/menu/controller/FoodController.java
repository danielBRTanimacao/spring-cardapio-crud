package com.daniel.menu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.daniel.menu.dto.*;
import com.daniel.menu.entity.Food;
import com.daniel.menu.repository.FoodRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("food")
public class FoodController {

    @Autowired
    private FoodRepository repository;
    
    @GetMapping
    public List<FoodResponseDTO> getAll() {
        List<FoodResponseDTO> foodList = repository
            .findAll()
            .stream()
            .map(FoodResponseDTO::new).toList();
        return foodList;
    }

    @PostMapping
    public ResponseEntity<Food> saveNewFood(@Valid @RequestBody FoodRequestDTO data) {
        Food foodData = new Food(data);
        repository.save(foodData);
        return ResponseEntity.status(HttpStatus.CREATED).body(foodData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFood(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comida não encontrada");
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFood(@PathVariable Long id, @Valid @RequestBody FoodRequestDTO data) {
        var specificFood = repository.findById(id);

        if (specificFood.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comida não encontrada!");
        }

        Food existingFood = specificFood.get();
        existingFood.setTitle(data.title());
        existingFood.setImage(data.image());
        existingFood.setPrice(data.price());

        repository.save(existingFood);
        
        return ResponseEntity.ok(existingFood);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

}
