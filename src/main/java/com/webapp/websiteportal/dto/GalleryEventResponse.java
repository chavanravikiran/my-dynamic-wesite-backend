package com.webapp.websiteportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GalleryEventResponse {

	private String type;
    private String name;
    
    public GalleryEventResponse(String type) {
        this.type = type;
        this.name = type.substring(0, 1).toUpperCase() + type.substring(1);
    }
}
