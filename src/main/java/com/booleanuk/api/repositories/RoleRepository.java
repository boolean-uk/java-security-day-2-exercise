package com.booleanuk.api.repositories;

import com.booleanuk.api.models.Role;
import com.booleanuk.api.models.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleType name);
}