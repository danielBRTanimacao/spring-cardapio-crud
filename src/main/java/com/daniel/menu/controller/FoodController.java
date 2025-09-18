package com.daniel.menu.controller;

import java.util.*;

import com.daniel.menu.service.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.daniel.menu.dto.*;
import com.daniel.menu.entity.Food;

import jakarta.validation.Valid;

@Tag(name = "Food Controllers", description = "Endpoints to controller food")
@RestController
@RequestMapping("api/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;


    @Operation(
            summary = "Pagination request all Foods",
            description = "request and paginate all foods on db"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "find page and size of a request, default is 0, 10"
            )
    })
    @GetMapping
    public Page<Food> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return foodService.getAllFoods(page, size);
    }

    @Operation(
            summary = "Create and save new Food",
            description = "create new food on db title, image, value"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created new Food"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid arguments submitted use form-raw to submit"
            )
    })
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
