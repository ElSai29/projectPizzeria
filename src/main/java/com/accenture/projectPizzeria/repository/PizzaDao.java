package com.accenture.projectPizzeria.repository;

import com.accenture.projectPizzeria.repository.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PizzaDao extends JpaRepository<Pizza, UUID> {
}
