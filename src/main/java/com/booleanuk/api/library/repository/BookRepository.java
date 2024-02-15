package com.booleanuk.api.library.repository;

import com.booleanuk.api.library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
