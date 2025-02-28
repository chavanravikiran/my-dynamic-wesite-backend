package com.webapp.websiteportal.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.webapp.websiteportal.dto.FeatureListResponse;
import com.webapp.websiteportal.dto.FeatureMenuResponse;
import com.webapp.websiteportal.dto.FileModelResponse;
import com.webapp.websiteportal.dto.WebSiteDetailsResponse;
import com.webapp.websiteportal.dto.WebsiteResponse;
import com.webapp.websiteportal.entity.WebSiteDetails;
import com.webapp.websiteportal.entity.WebsiteFeatureMenu;
import com.webapp.websiteportal.repository.WebsiteDetailsRepository;
import com.webapp.websiteportal.repository.WebsiteFeatureMenuRepository;
import com.webapp.websiteportal.service.IS3Service;
import com.webapp.websiteportal.service.IWebsiteDetailsService;

@Service
public class WebsiteDetailsServiceImpl implements IWebsiteDetailsService{

	@Autowired
	private WebsiteDetailsRepository websiteDetailsRepository;
	
	@Autowired
	private WebsiteFeatureMenuRepository websiteFeatureMenuRepository;
	
	@Autowired
	private IS3Service s3Service;
	
//	@Value("${application.rootpath}")
//    private String rootpath;
	
	@Value("${application.rootpath.logoImage}")
    private String logoImage;
	
	@Override
	public WebsiteResponse fetchWebSiteDetails() throws IOException {
		Optional<WebSiteDetails> webSiteDetails = websiteDetailsRepository.findByKeyAndIsActive(1L,'Y');
		if(webSiteDetails.isPresent()) {
			String folderPath=logoImage+"/";
			FileModelResponse response = s3Service.downloadFileWithPath(webSiteDetails.get().getWebsiteLogo(),folderPath);
			return WebsiteResponse.init(webSiteDetails,response.getBase64());
		}
		return null;
	}

//	@Override
//	public Optional<WebSiteDetails> findByKeyAndIsActive(Long websiteId, Character isActive) {
//		return websiteDetailsRepository.findByKeyAndIsActive(websiteId,isActive);
//	}

	
//	@Override
//	public WebSiteDetailsResponse getWebsiteDetailsById(Long websiteId) {
//        Optional<WebSiteDetails> webSiteDetails = websiteDetailsRepository.findByKeyAndIsActive(websiteId,'Y');
//        if(webSiteDetails.isPresent()) {
//        	
//        }else {
//        	System.out.println("Website not found with id: " + websiteId);
//        }
//        // Map the entity to the response DTO
//        WebSiteDetailsResponse response = new WebSiteDetailsResponse();
//        response.setKey(webSiteDetails.get().getKey());
//        response.setWebsiteName(webSiteDetails.get().getWebsiteName());
//        response.setWebsiteLogo(webSiteDetails.get().getWebsiteLogo());
//        response.setWebSiteType(webSiteDetails.get().getWebSiteType().toString());
//
////        List<FeatureListResponse> featureList = webSiteDetails.get().getWebsiteFeatureMenus().stream()
////            .map(websiteFeatureMenu -> {
////                FeatureListResponse featureListResponse = new FeatureListResponse();
////                FeatureMenuResponse featureMenuResponse = new FeatureMenuResponse();
////
////                featureMenuResponse.setKey(websiteFeatureMenu.getFeatureMenu().getKey());
////                featureMenuResponse.setFeatureName(websiteFeatureMenu.getFeatureMenu().getFeatureName());
////                featureMenuResponse.setFeatureNameMr(websiteFeatureMenu.getFeatureMenu().getFeatureNameMr());
////                featureMenuResponse.setDisplaySeq(websiteFeatureMenu.getDisplaySeq());
////
////                featureListResponse.setFeatureMenu(featureMenuResponse);
////                return featureListResponse;
////            })
////            .collect(Collectors.toList());
//
//        List<WebsiteFeatureMenu>  websiteFeatureMenu = websiteFeatureMenuRepository.findByIsActiveAndWebsiteDetails('Y',webSiteDetails.get());
//        
//        response.setFeatureList(featureList);
//        return response;
//    }
	
	@Override
	public WebSiteDetailsResponse getWebsiteDetailsById(Long websiteId) {
	    WebSiteDetails webSiteDetails = websiteDetailsRepository.findByKeyAndIsActive(websiteId, 'Y')
	        .orElseThrow(() -> new RuntimeException("Website not found with id: " + websiteId));

	    WebSiteDetailsResponse response = new WebSiteDetailsResponse();
	    response.setKey(webSiteDetails.getKey());
	    response.setWebsiteName(webSiteDetails.getWebsiteName());
	    response.setWebsiteLogo(webSiteDetails.getWebsiteLogo());
	    response.setWebSiteType(webSiteDetails.getWebSiteType().toString());

	    List<WebsiteFeatureMenu> websiteFeatureMenus = websiteFeatureMenuRepository.findByIsActiveAndWebsiteDetails('Y', webSiteDetails);

	    List<FeatureListResponse> featureList = websiteFeatureMenus.stream()
	        .map(websiteFeatureMenu -> {
	            FeatureMenuResponse featureMenuResponse = new FeatureMenuResponse();
	            featureMenuResponse.setKey(websiteFeatureMenu.getFeatureMenu().getKey());
	            featureMenuResponse.setFeatureName(websiteFeatureMenu.getFeatureMenu().getFeatureName());
	            featureMenuResponse.setFeatureNameMr(websiteFeatureMenu.getFeatureMenu().getFeatureNameMr());
	            featureMenuResponse.setDisplaySeq(websiteFeatureMenu.getDisplaySeq());

	            FeatureListResponse featureListResponse = new FeatureListResponse();
	            featureListResponse.setFeatureMenu(featureMenuResponse);

	            return featureListResponse;
	        })
	        .collect(Collectors.toList());

	    response.setFeatureList(featureList);
	    return response;
	}

	@Override
	public WebSiteDetailsResponse getWebsiteDetailsById(Long websiteId, FileModelResponse response1) {
		 WebSiteDetails webSiteDetails = websiteDetailsRepository.findByKeyAndIsActive(websiteId, 'Y')
			        .orElseThrow(() -> new RuntimeException("Website not found with id: " + websiteId));

			    WebSiteDetailsResponse response = new WebSiteDetailsResponse();
			    response.setKey(webSiteDetails.getKey());
			    response.setWebsiteName(webSiteDetails.getWebsiteName());
			    response.setWebsiteLogo(response1.getBase64());
			    response.setWebSiteType(webSiteDetails.getWebSiteType().toString());

			    List<WebsiteFeatureMenu> websiteFeatureMenus = websiteFeatureMenuRepository.findByIsActiveAndWebsiteDetails('Y', webSiteDetails);

			    List<FeatureListResponse> featureList = websiteFeatureMenus.stream()
			        .map(websiteFeatureMenu -> {
			            FeatureMenuResponse featureMenuResponse = new FeatureMenuResponse();
			            featureMenuResponse.setKey(websiteFeatureMenu.getFeatureMenu().getKey());
			            featureMenuResponse.setFeatureName(websiteFeatureMenu.getFeatureMenu().getFeatureName());
			            featureMenuResponse.setFeatureNameMr(websiteFeatureMenu.getFeatureMenu().getFeatureNameMr());
			            featureMenuResponse.setDisplaySeq(websiteFeatureMenu.getDisplaySeq());

			            FeatureListResponse featureListResponse = new FeatureListResponse();
			            featureListResponse.setFeatureMenu(featureMenuResponse);

			            return featureListResponse;
			        })
			        .collect(Collectors.toList());

			    response.setFeatureList(featureList);
			    return response;
	}
}
