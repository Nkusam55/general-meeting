package com.liafi.gcmeeting.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liafi.gcmeeting.entity.Relative;



public interface RelativesRepository extends JpaRepository<Relative, Long> {
	
	List<Relative> findByUserId(Long userId);

	@Transactional
	void deleteByUserId(long userId);
}


