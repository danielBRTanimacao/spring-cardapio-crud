package com.daniel.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daniel.menu.food.Food;

public interface FoodRepository extends JpaRepository<Food, Long>{

}  
  
