package com.webservice.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webservice.library.entities.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{}
