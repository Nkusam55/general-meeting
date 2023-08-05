package com.liafi.gcmeeting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liafi.gcmeeting.entity.Role;

/**
 * @author narendra kusam
 */

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
