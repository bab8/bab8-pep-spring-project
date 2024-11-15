package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("FROM Account WHERE username = :user AND password = :pass")
    Account login(@Param("user") String user, @Param("pass") String password);

    @Query("FROM Account WHERE username = :user")
    Account userExists(@Param("user") String user);

}
