package com.webapp.websiteportal.service;

import com.webapp.websiteportal.entity.Users;

public interface IReportGeneration {

	String generateBookingReceipt(Long bookingId, Users currentUser) throws Exception;

	
}
