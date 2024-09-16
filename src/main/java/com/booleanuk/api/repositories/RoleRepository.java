package com.booleanuk.api.repositories;

import com.booleanuk.api.models.ERole;
import com.booleanuk.api.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
