package com.daniel.menu.service;

import com.daniel.menu.component.TokenGenerator;
import com.daniel.menu.dto.FoodRequestDTO;
import com.daniel.menu.entity.Food;
import com.daniel.menu.exceptions.customs.NotFoundException;
import com.daniel.menu.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;


@RequiredArgsConstructor
@Slf4j
@Service
public class FoodService {
    private final FoodRepository foodRepository;

    @Value("${spring.upload.dir}")
    private String PATH_UPLOAD;

    public Page<Food> getAllFoods(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        log.info("Page requested: {}", pageable);
        return foodRepository.findAll(pageable);
    }


    public void createFood(FoodRequestDTO data) {
        try {
            Path uploadFile = Paths.get(this.PATH_UPLOAD);
            log.info("Uploading directory file: {}", uploadFile);
            if(!Files.exists(uploadFile)) {
                Files.createDirectories(uploadFile);
            }

            log.debug("Generating new token for file");
            String filename = TokenGenerator.generateToken() + "-" + data.image().getOriginalFilename();
            log.info("Creating food file: {}", filename);
            Path filePath = uploadFile.resolve(filename);
            data.image().transferTo(new File(filePath.toAbsolutePath().toString()));
            log.info("Uploaded file: {}", filePath);

            Food food = new Food();
            food.setImage(filename);
            food.setTitle(data.title());
            food.setPrice(data.price());
            foodRepository.save(food);
            log.info("Food created: {}", food);

        } catch (IOException | IllegalStateException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("Could not save image food", e);
        }
    }

    public void delFood(Long id) {
        log.debug("Searching for food with id: {}", id);
        foodRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Food Not Found")
        );

        foodRepository.deleteById(id);
        log.info("Food deleted: {}", id);
    }

    public void updateFood(Long id, Food preFood) {
        log.debug("Finding food with id: {}", id);
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
        log.info("Food updated: {}", specificFood);
    }
}
