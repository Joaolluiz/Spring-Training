package com.webservice.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webservice.library.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

}
