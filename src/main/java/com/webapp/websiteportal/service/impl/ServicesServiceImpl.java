package com.webapp.websiteportal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.websiteportal.dto.ServiceResponseDTO;
import com.webapp.websiteportal.entity.ServicesFeature;
import com.webapp.websiteportal.entity.WebSiteDetails;
import com.webapp.websiteportal.repository.FeatureMenuRepository;
import com.webapp.websiteportal.repository.ServicesRepository;
import com.webapp.websiteportal.repository.WebsiteDetailsRepository;
import com.webapp.websiteportal.service.IServicesService;

@Service
public class ServicesServiceImpl implements IServicesService{

	@Autowired
	private WebsiteDetailsRepository websiteDetailsRepository;
	
	@Autowired
	private FeatureMenuRepository featureMenuRepository;

	@Autowired
	private ServicesRepository servicesRepository;
	
	@Override
	public List<ServiceResponseDTO> fetchActiveServices(WebSiteDetails webSiteDetail) {
		List<ServicesFeature> services = servicesRepository.findByWebSiteDetailsAndIsActive(webSiteDetail.getKey(),'Y');
		return ServiceResponseDTO.init(services);
	}

	
}
