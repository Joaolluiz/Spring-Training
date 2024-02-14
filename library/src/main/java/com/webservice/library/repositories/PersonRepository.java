package com.webservice.library.repositories;

import com.webservice.library.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.webservice.library.entities.Person;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long>{
    @Modifying
    @Query("UPDATE Person p SET p.enabled = false  WHERE p.id =:id")
    void disablePerson(@Param("id") Long id);
}
