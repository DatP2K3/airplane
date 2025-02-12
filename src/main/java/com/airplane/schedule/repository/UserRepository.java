package com.airplane.schedule.repository;

import com.airplane.schedule.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
