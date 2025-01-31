package com.webapp.websiteportal.dto;

import java.util.List;

import lombok.Data;

@Data
public class WebSiteDetailsResponse {
    private Long key;
    private String websiteName;
    private String websiteLogo;
    private String webSiteType;
    private List<FeatureListResponse> featureList;
}
