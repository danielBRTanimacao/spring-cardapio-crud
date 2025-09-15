package com.daniel.menu.service;

import com.daniel.menu.entity.Food;
import com.daniel.menu.exceptions.customs.NotFoundException;
import com.daniel.menu.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;

    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    public void createFood(Food food) {
        foodRepository.save(food);
    }

    public ResponseEntity<Void> delFood(Long id) {
        if (!foodRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        foodRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Void> updateFood(Long id, Food preFood) {
        var specificFood = foodRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Food Not Found")
        );

        if (preFood.getTitle() !=  null && !preFood.getTitle().isEmpty()) {
            specificFood.setTitle(preFood.getTitle());
        }
        if (preFood.getImage() !=  null && !preFood.getImage().isEmpty()) {
            specificFood.setImage(preFood.getImage());
        }
        if (Objects.nonNull(preFood.getPrice())) {
            specificFood.setPrice(preFood.getPrice());
        }
        foodRepository.save(specificFood);
        return ResponseEntity.ok().build();
    }
}
