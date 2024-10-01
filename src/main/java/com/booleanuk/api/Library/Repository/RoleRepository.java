package com.booleanuk.api.Library.Repository;

import com.booleanuk.api.Library.Model.ERole;
import com.booleanuk.api.Library.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
