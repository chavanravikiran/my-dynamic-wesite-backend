package com.webapp.websiteportal.dto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.webapp.websiteportal.entity.WebSiteDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebsiteResponse extends MessageResponse{
	
	private static final long serialVersionUID = 5820582155836361527L;

	private Long key;
	
	private String websiteName;
	
	private String websiteNameMr;
	
	private String oldWebsiteLink;

	private String websiteLogo;
	
	private String webSiteType;
	
	private Character isActive;
	
	public static WebsiteResponse init(Optional<WebSiteDetails> webSiteDetails) {
		return WebsiteResponse.builder()
				.key(webSiteDetails.get().getKey())
				.websiteName(webSiteDetails.get().getWebsiteName())
				.websiteNameMr(webSiteDetails.get().getWebsiteNameMr())
				.oldWebsiteLink(webSiteDetails.get().getOldWebsiteLink())
				.websiteLogo(webSiteDetails.get().getWebsiteLogo())
				.status(ServiceStatus.SUCCESS)
				.build();
	}

	public static WebsiteResponse init(Optional<WebSiteDetails> webSiteDetails, String base64) {
		return WebsiteResponse.builder()
				.key(webSiteDetails.get().getKey())
				.websiteName(webSiteDetails.get().getWebsiteName())
				.websiteNameMr(webSiteDetails.get().getWebsiteNameMr())
				.oldWebsiteLink(webSiteDetails.get().getOldWebsiteLink())
				.websiteLogo(base64)
				.status(ServiceStatus.SUCCESS)
				.build();
	}

	public static List<WebsiteResponse> init(List<WebSiteDetails> webSiteDetailsList) {
        return webSiteDetailsList.stream()
                .map(detail -> WebsiteResponse.builder()
                        .key(detail.getKey())
                        .websiteName(detail.getWebsiteName())
                        .websiteNameMr(detail.getWebsiteNameMr())
                        .webSiteType(detail.getWebSiteType().toString())
                        .status(ServiceStatus.SUCCESS)
                        .isActive(detail.getIsActive())
                        .build())
                .collect(Collectors.toList());
    }
}
