package com.Security.demo.SpringSecurity.Repository;
import com.Security.demo.SpringSecurity.Entity.Accounts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountsRepository extends CrudRepository<Accounts, Long> {
    Accounts findByCustomerId(int customerId);
}
