package com.webapp.websiteportal.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GalleryResponse {
	
	private List<GalleryEventResponse> events;
    private List<GalleryImageResponse> images;
    
}
