package com.Security.demo.SpringSecurity.Repository;

import com.Security.demo.SpringSecurity.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer>findByEmail(String email);

}
