package com.generalbody.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.generalbody.entity.User;

/**
 * @author narendra kusam
 */

public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query("SELECT user FROM User user where user.status = :status")
	List<User> findUserByStatus(@Param("status") boolean status);
	
    @Query("SELECT user FROM User user where user.status = :status and email = :email")
	User findByEmail(@Param("status") boolean status, @Param("email") String mailId);
}
