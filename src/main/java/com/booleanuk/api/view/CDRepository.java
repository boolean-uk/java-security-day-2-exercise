package com.booleanuk.api.view;

import com.booleanuk.api.model.CD;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CDRepository extends JpaRepository<CD, Integer> {
}
