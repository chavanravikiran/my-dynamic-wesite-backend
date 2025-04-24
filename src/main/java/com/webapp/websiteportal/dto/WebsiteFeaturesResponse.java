package com.webapp.websiteportal.dto;

import java.util.List;
import java.util.Optional;

import com.webapp.websiteportal.entity.WebSiteDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class WebsiteFeaturesResponse extends MessageResponse{
	
	private static final long serialVersionUID = -7771473997991086502L;
	
	private Long key;
    private String websiteName;
    private String websiteNameMr;
    private String oldWebsiteLink;
    private String websiteLogo;
    private String webSiteType;
    private List<FeatureMenuResponse> featureList;
    private ServiceStatus status;

	public static WebsiteFeaturesResponse init(Optional<WebSiteDetails> webSiteDetails) {
		return WebsiteFeaturesResponse.builder()
				.key(webSiteDetails.get().getKey())
				.websiteName(webSiteDetails.get().getWebsiteName())
				.websiteNameMr(webSiteDetails.get().getWebsiteNameMr())
				.oldWebsiteLink(webSiteDetails.get().getOldWebsiteLink())
				.websiteLogo(webSiteDetails.get().getWebsiteLogo())
				.status(ServiceStatus.SUCCESS)
				.build();
	}
}
