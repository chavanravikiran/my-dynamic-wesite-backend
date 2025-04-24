package com.webapp.websiteportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.websiteportal.entity.Role;
import com.webapp.websiteportal.entity.WebsiteRoleFeatureMenu;

@Repository
public interface WebsiteRoleFeatureMenuRepository extends JpaRepository<WebsiteRoleFeatureMenu, Long> {
    
    List<WebsiteRoleFeatureMenu> findByWebsiteDetailsKeyAndRole(Long websiteKey, Role role);
    
    List<WebsiteRoleFeatureMenu> findByRole(Role role);

    List<WebsiteRoleFeatureMenu> findByWebsiteDetailsKey(Long websiteKey);
}