package com.example.lab001.repository;

import com.example.lab001.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.id IN :ids")
    List<User> findAllById(@Param("ids") Set<Long> ids);

    @Query("SELECT u.email FROM User u WHERE u.email IN :emails")
    List<String> findEmailsExistingIn(@Param("emails") List<String> emails);
}