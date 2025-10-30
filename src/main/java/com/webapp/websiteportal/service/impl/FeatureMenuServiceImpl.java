package com.webapp.websiteportal.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.websiteportal.dto.FeatureMenuRequest;
import com.webapp.websiteportal.dto.MessageResponse;
import com.webapp.websiteportal.entity.FeatureMenu;
import com.webapp.websiteportal.repository.FeatureMenuRepository;
import com.webapp.websiteportal.repository.WebsiteDetailsRepository;
import com.webapp.websiteportal.service.IFeatureMenuService;

@Service
public class FeatureMenuServiceImpl implements IFeatureMenuService{

	@Autowired
	private WebsiteDetailsRepository websiteDetailsRepository;
	
	@Autowired
	private FeatureMenuRepository featureMenuRepository;

	@Override
	public List<FeatureMenu> findByIsActive(Character flag) {
		return featureMenuRepository.findByIsActive('Y');
	}

	@Override
	public List<FeatureMenu> findAllByOrderByKeyAsc() {
		return featureMenuRepository.findAllByOrderByKeyAsc();
	}

	@Override
	public MessageResponse addNewFeature(FeatureMenuRequest featureMenuRequest) {
		Optional<FeatureMenu> featureMenu = featureMenuRepository.findByFeatureName(featureMenuRequest.getFeatureName());
		if(featureMenu.isPresent()) {
			if(featureMenu.get().getIsActive().equals('N')) {
				return MessageResponse.successWithMsg("Present But Deactivate Feature !!");
			}
		}else {
			FeatureMenu featureMenuObj = new FeatureMenu();
			featureMenuObj.setFeatureName(featureMenuRequest.getFeatureName());
			featureMenuObj.setFeatureNameMr(featureMenuRequest.getFeatureNameMr());
			featureMenuObj.setFeatureIcon(featureMenuRequest.getFeatureIcon());
			featureMenuObj.setDisplaySeq(featureMenuRequest.getDisplaySeq());
			featureMenuRepository.save(featureMenuObj);
			return MessageResponse.successWithMsg("Feature Created !!");
		}
		return null;
	}

}
