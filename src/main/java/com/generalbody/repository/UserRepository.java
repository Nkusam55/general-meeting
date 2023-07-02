package com.generalbody.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generalbody.entity.User;

/**
 * @author narendra kusam
 */

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String mailId);
}
