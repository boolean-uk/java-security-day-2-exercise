package com.booleanuk.Library.repository;

import com.booleanuk.Library.models.ERole;
import com.booleanuk.Library.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
