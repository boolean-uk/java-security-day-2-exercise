package com.booleanuk.api.repository;

import com.booleanuk.api.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
