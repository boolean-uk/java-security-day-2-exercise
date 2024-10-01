package com.booleanuk.api.Library.Repository;

import com.booleanuk.api.Library.Model.CD;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CDRepository extends JpaRepository<CD, Integer> {
}
