package com.webapp.websiteportal.service;

import com.webapp.websiteportal.dto.GalleryResponse;
import com.webapp.websiteportal.entity.WebSiteType;

public interface IGalleryService {

	GalleryResponse getGalleryByWebsiteType(WebSiteType websiteType);

}
