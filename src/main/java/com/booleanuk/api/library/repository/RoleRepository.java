package com.booleanuk.api.library.repository;

import com.booleanuk.api.library.model.ERole;
import com.booleanuk.api.library.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);

    boolean existsByName(ERole name);
}
