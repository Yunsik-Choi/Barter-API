package com.project.barter.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUserByUserId(String userId);

    Optional<User> findUserByUserIdAndPassword(String userId, String password);

    @Modifying
    @Query(
            value = "truncate table user",
            nativeQuery = true
    )
    void truncateMyTable();
}
