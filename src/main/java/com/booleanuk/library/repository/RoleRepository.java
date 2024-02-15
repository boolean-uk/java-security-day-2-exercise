package com.booleanuk.library.repository;

import com.booleanuk.library.models.ERole;
import com.booleanuk.library.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
Optional<Role> findByName(ERole name);
}
