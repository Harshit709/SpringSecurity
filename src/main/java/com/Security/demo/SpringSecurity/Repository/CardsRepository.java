package com.Security.demo.SpringSecurity.Repository;

import com.Security.demo.SpringSecurity.Entity.Cards;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardsRepository extends CrudRepository<Cards, Long> {
    List<Cards> findByCustomerId(int customerId);
}
