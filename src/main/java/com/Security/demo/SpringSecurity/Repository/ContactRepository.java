package com.Security.demo.SpringSecurity.Repository;

import com.Security.demo.SpringSecurity.Entity.Contact;
import org.springframework.data.repository.CrudRepository;

public interface ContactRepository  extends CrudRepository<Contact, Long> {
}
