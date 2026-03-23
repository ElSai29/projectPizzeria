package com.accenture.projectPizzeria.service;


import com.accenture.projectPizzeria.mapper.CustomerMapper;
import com.accenture.projectPizzeria.repository.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = CustomerServiceImpl.class)

public class CustomerServiceImplIntegrationTest {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    private CustomerService customerService;

}
