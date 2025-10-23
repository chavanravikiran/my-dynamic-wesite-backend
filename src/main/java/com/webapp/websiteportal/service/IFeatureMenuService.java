package com.webapp.websiteportal.service;

import java.util.List;

import com.webapp.websiteportal.entity.FeatureMenu;

public interface IFeatureMenuService {

	List<FeatureMenu> findByIsActive(Character flag);

	List<FeatureMenu> findAllByOrderByKeyAsc();


}
