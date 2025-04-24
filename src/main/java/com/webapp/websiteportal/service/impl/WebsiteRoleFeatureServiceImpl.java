package com.webapp.websiteportal.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.webapp.websiteportal.dto.FeatureMenuResponse;
import com.webapp.websiteportal.entity.FeatureMenu;
import com.webapp.websiteportal.entity.Users;
import com.webapp.websiteportal.entity.WebsiteRoleFeatureMenu;
import com.webapp.websiteportal.repository.UserRepository;
import com.webapp.websiteportal.repository.WebsiteRoleFeatureMenuRepository;
import com.webapp.websiteportal.service.IWebsiteRoleFeatureService;

@Service
public class WebsiteRoleFeatureServiceImpl implements IWebsiteRoleFeatureService{

	@Autowired
	private WebsiteRoleFeatureMenuRepository repository;
	
	@Autowired
    private UserRepository usersRepository;
	
//	@Override
//	public List<FeatureMenu> getUserWiseFeatures(Long userId) {
//        Users user = usersRepository.findById(userId)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        List<WebsiteRoleFeatureMenu> mappings = repository.findByWebsiteDetailsKeyAndRole(
//                user.getWebSiteDetails().getKey(), user.getRole());
//
//        return mappings.stream()
//                .map(WebsiteRoleFeatureMenu::getFeatureMenu)
//                .collect(Collectors.toList());
//    }
	@Override
	public List<FeatureMenuResponse> getUserWiseFeatures(Long userId) {
	    Users user = usersRepository.findById(userId)
	            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

	    List<WebsiteRoleFeatureMenu> mappings = repository.findByWebsiteDetailsKeyAndRole(
	            user.getWebSiteDetails().getKey(), user.getRole());

	    return mappings.stream()
	            .map(mapping -> {
	                FeatureMenu f = mapping.getFeatureMenu();
	                return new FeatureMenuResponse(f.getKey(), f.getFeatureName(), f.getFeatureNameMr(), f.getFeatureIcon(), f.getDisplaySeq());
	            })
	            .collect(Collectors.toList());
	}

}
