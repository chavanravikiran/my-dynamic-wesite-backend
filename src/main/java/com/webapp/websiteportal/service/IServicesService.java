package com.webapp.websiteportal.service;

import java.util.List;

import com.webapp.websiteportal.dto.ServiceResponseDTO;
import com.webapp.websiteportal.entity.ServicesFeature;
import com.webapp.websiteportal.entity.WebSiteDetails;

public interface IServicesService {

	List<ServiceResponseDTO> fetchActiveServices(WebSiteDetails webSiteDetail);

}
