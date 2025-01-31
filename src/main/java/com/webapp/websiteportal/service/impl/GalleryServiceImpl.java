package com.webapp.websiteportal.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.websiteportal.dto.GalleryEventResponse;
import com.webapp.websiteportal.dto.GalleryImageResponse;
import com.webapp.websiteportal.dto.GalleryResponse;
import com.webapp.websiteportal.entity.GalleryFeature;
import com.webapp.websiteportal.entity.WebSiteDetails;
import com.webapp.websiteportal.entity.WebSiteType;
import com.webapp.websiteportal.repository.FeatureMenuRepository;
import com.webapp.websiteportal.repository.GalleryFeatureRepository;
import com.webapp.websiteportal.repository.ServicesRepository;
import com.webapp.websiteportal.repository.WebsiteDetailsRepository;
import com.webapp.websiteportal.service.IGalleryService;

@Service
public class GalleryServiceImpl implements IGalleryService{

	@Autowired
	private WebsiteDetailsRepository websiteDetailsRepository;
	
	@Autowired
	private FeatureMenuRepository featureMenuRepository;

	@Autowired
	private ServicesRepository servicesRepository;
	
	@Autowired
	private GalleryFeatureRepository galleryFeatureRepository;

	@Override
	public GalleryResponse getGalleryByWebsiteType(WebSiteType websiteType) {
		Optional<WebSiteDetails> websiteDetails = websiteDetailsRepository.findByWebSiteType(websiteType);

        if (websiteDetails.isEmpty()) {
            return null;
        }

        List<GalleryFeature> galleryFeatures = galleryFeatureRepository.findByWebSiteDetailsAndIsActive(websiteDetails.get(), 'Y');

        List<GalleryImageResponse> images = galleryFeatures.stream()
            .map(feature -> new GalleryImageResponse(
                "/assets/images/" + feature.getImageName(),
                feature.getAltImageName() != null ? feature.getAltImageName() : feature.getImageName(),
                feature.getGalleryType()
            ))
            .collect(Collectors.toList());

        Set<String> eventTypes = galleryFeatures.stream()
            .map(GalleryFeature::getGalleryType)
            .collect(Collectors.toSet());

        List<GalleryEventResponse> events = eventTypes.stream()
            .map(GalleryEventResponse::new)
            .collect(Collectors.toList());

        return new GalleryResponse(events, images);
    }
	
}
