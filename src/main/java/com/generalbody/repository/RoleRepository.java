package com.generalbody.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generalbody.entity.Role;

/**
 * @author narendra kusam
 */

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
