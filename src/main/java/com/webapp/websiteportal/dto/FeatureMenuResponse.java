package com.webapp.websiteportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FeatureMenuResponse{
	
	private Long key;
    private String featureName;
    private String featureNameMr;
    private String featureIcon;
    private Long displaySeq;
}
