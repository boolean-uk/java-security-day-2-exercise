package com.booleanuk.api.Library.Repository;

import com.booleanuk.api.Library.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
