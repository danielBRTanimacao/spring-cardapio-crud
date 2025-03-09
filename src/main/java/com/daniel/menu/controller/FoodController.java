package com.daniel.menu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

}
