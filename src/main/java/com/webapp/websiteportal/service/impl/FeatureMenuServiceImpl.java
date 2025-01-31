package com.webapp.websiteportal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.websiteportal.repository.FeatureMenuRepository;
import com.webapp.websiteportal.repository.WebsiteDetailsRepository;
import com.webapp.websiteportal.service.IFeatureMenuService;

@Service
public class FeatureMenuServiceImpl implements IFeatureMenuService{

	@Autowired
	private WebsiteDetailsRepository websiteDetailsRepository;
	
	@Autowired
	private FeatureMenuRepository featureMenuRepository;

}
