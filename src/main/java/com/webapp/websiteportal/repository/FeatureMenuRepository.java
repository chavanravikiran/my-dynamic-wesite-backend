package com.webapp.websiteportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.websiteportal.entity.FeatureMenu;

@Repository
public interface FeatureMenuRepository extends JpaRepository<FeatureMenu, Long> {

}
