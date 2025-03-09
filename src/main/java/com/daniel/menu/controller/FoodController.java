package com.daniel.menu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.daniel.menu.dto.*;
import com.daniel.menu.entity.Food;
import com.daniel.menu.repository.FoodRepository;

import lombok.var;


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
    public void saveNewFood(@RequestBody FoodRequestDTO data) {
        Food foodData = new Food(data);
        repository.save(foodData);
    }

    @DeleteMapping("/{id}")
    public void deleteFood(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PutMapping("/{name}/{id}")
    public void putFood(@PathVariable String name, @PathVariable Long id, @RequestBody FoodRequestDTO data) {
        var specificFood = repository.findById(id);

        if (specificFood.isPresent()) {
            Food existingFood = specificFood.get();
    
            existingFood.setTitle(data.title());
            existingFood.setImage(data.image());
            existingFood.setPrice(data.price());
    
            repository.save(existingFood);
    
        } 
        
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
