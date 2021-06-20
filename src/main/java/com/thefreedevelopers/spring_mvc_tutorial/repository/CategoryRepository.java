package com.thefreedevelopers.spring_mvc_tutorial.repository;

import com.thefreedevelopers.spring_mvc_tutorial.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
