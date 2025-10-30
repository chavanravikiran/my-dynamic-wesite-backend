package com.webapp.websiteportal.service;

import java.util.List;

import com.webapp.websiteportal.dto.FeatureMenuRequest;
import com.webapp.websiteportal.dto.MessageResponse;
import com.webapp.websiteportal.entity.FeatureMenu;

public interface IFeatureMenuService {

	List<FeatureMenu> findByIsActive(Character flag);

	List<FeatureMenu> findAllByOrderByKeyAsc();

	MessageResponse addNewFeature(FeatureMenuRequest featureMenuRequest);


}
