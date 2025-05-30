package com.focus.lit.repository;

import com.focus.lit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(int id);
    Optional<User> findByMail(String mail);
    Optional<User> findByEmailVerificationLink(String emailVerificationLink);

}

