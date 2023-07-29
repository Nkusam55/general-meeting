package com.generalbody.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.generalbody.entity.User;

/**
 * @author narendra kusam
 */

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query("SELECT user FROM User user where user.status = :status")
	List<User> findUserByStatus(@Param("status") boolean status);
	
    @Query("SELECT user FROM User user where user.status = :status and email = :email")
	User findByEmail(@Param("status") boolean status, @Param("email") String mailId);

    @Modifying
    @Query("UPDATE User user set user.status = ?1 where user.id = ?2")
	int activateUserStatus(boolean status, long userId);
}
