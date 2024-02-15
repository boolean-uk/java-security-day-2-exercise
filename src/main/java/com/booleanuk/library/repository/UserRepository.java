package com.booleanuk.library.repository;

import com.booleanuk.library.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);   //"Username" måste matcha fält i modellen. spring skapar själv denna metod. optional = user may not exist

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
