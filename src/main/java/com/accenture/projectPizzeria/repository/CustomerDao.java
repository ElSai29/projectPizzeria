package com.accenture.projectPizzeria.repository;

import com.accenture.projectPizzeria.repository.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerDao extends JpaRepository<Customer, UUID> {

   Customer findByNameCustomer(String name);
}
