package com.Security.demo.SpringSecurity.Repository;
import java.util.List;

import com.Security.demo.SpringSecurity.Entity.AccountTransactions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountTransactionsRepository extends  CrudRepository<AccountTransactions, Long>  {
    List<AccountTransactions> findByCustomerIdOrderByTransactionDtDesc(int customerId);
}
