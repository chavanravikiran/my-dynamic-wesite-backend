package com.webapp.websiteportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GalleryImageResponse {

	private String url;
	private String alt;
	private String type;

//	public GalleryImageResponse(String url, String alt, String type) {
//		this.url = url;
//		this.alt = alt;
//		this.type = type;
//	}
//
//	public String getUrl() {
//		return url;
//	}
//
//	public void setUrl(String url) {
//		this.url = url;
//	}
//
//	public String getAlt() {
//		return alt;
//	}
//
//	public void setAlt(String alt) {
//		this.alt = alt;
//	}
//
//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}
}
