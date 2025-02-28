package com.webapp.websiteportal.util;

import java.util.Base64;

public class Utils {

	public byte[] saveUserProfile(String base64String) {
	    // Decode Base64 before storing
	    byte[] decodedBytes = Base64.getDecoder().decode(base64String);
	    return decodedBytes;
	}
}
