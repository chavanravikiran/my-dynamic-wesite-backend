package com.webapp.websiteportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.websiteportal.entity.FeatureMenu;

@Repository
public interface FeatureMenuRepository extends JpaRepository<FeatureMenu, Long> {

	List<FeatureMenu> findByIsActive(Character flag);

	List<FeatureMenu> findAllByOrderByKeyAsc();

}
