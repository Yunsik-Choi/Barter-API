package com.project.barter.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUserByUserId(String userId);

    Optional<User> findUserByUserIdAndPassword(String userId, String password);

}
