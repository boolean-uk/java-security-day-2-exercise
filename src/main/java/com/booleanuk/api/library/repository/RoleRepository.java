package com.booleanuk.api.library.repository;

import com.booleanuk.api.library.models.ERole;
import com.booleanuk.api.library.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole nAME);

    Boolean existsByName(String name);

}
