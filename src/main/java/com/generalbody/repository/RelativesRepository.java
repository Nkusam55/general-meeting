package com.generalbody.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generalbody.entity.Relative;


public class RelativesRepository {

	public interface ClubListRepository extends JpaRepository<Relative, Long> {
		List<Relative> findByTutorialId(Long userId);

		@Transactional
		void deleteByTutorialId(long userId);
	}


}
