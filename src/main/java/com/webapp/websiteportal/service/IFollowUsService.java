package com.webapp.websiteportal.service;

import java.util.List;

import com.webapp.websiteportal.entity.FollowUsFeature;
import com.webapp.websiteportal.entity.WebSiteDetails;

public interface IFollowUsService {

	List<FollowUsFeature> fetchActiveFollowUsData(WebSiteDetails webSiteDetail);


}
