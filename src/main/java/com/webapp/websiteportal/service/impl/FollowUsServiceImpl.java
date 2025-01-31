package com.webapp.websiteportal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.websiteportal.entity.FollowUsFeature;
import com.webapp.websiteportal.entity.WebSiteDetails;
import com.webapp.websiteportal.repository.FollowUsFeatureRepository;
import com.webapp.websiteportal.service.IFollowUsService;

@Service
public class FollowUsServiceImpl implements IFollowUsService{

	@Autowired
	private FollowUsFeatureRepository followUsFeatureRepository;
	
	@Override
	public List<FollowUsFeature> fetchActiveFollowUsData(WebSiteDetails webSiteDetail) {
		return followUsFeatureRepository.findByIsActiveAndWebSiteDetailsOrderByDisplaySeqAsc('Y',webSiteDetail);
	}

}
