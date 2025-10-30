package com.webapp.websiteportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeatureMenuRequest {

	private String featureName;
	private String featureNameMr;
	private String featureIcon;	
	private Long displaySeq;
}
