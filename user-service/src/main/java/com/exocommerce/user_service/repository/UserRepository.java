package com.exocommerce.user_service.repository;

import com.exocommerce.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Optional custom queries later
    boolean existsByEmail(String email);
}
