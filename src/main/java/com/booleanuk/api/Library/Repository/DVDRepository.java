package com.booleanuk.api.Library.Repository;

import com.booleanuk.api.Library.Model.DVD;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DVDRepository extends JpaRepository<DVD, Integer> {
}
