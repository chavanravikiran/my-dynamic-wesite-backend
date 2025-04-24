package com.webapp.websiteportal.service;

import java.util.List;
import com.webapp.websiteportal.dto.FeatureMenuResponse;

public interface IWebsiteRoleFeatureService {

//	public List<FeatureMenu> getUserWiseFeatures(Long userId);
	public List<FeatureMenuResponse> getUserWiseFeatures(Long userId);
}
