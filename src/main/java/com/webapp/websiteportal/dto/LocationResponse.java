package com.webapp.websiteportal.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.webapp.websiteportal.entity.FollowUsFeature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponse{
    
	private String platformName;
	private String iconName;
	private Long displaySeq;	
	private String fontAwesomeIcon;
	private String url;
	
	public static List<LocationResponse> init(List<FollowUsFeature> fetchActiveFollowUsData) {
		
		if (fetchActiveFollowUsData == null || fetchActiveFollowUsData.isEmpty()) {
	        return Collections.emptyList(); 
	    }
	    return fetchActiveFollowUsData.stream().map(feature -> LocationResponse.builder()
	            .platformName(feature.getPlatformName())
	            .iconName(feature.getIconName())
	            .displaySeq(feature.getDisplaySeq())
	            .fontAwesomeIcon(feature.getFontAwesomeIcon())
	            .url(feature.getUrl())
	            .build()
	    ).collect(Collectors.toList());
	}
	
	
}
