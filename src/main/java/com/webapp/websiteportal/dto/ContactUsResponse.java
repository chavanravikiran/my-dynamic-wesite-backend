package com.webapp.websiteportal.dto;

import java.util.List;

import com.webapp.websiteportal.entity.ContactUsFeature;
import com.webapp.websiteportal.entity.OfficeType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactUsResponse {

	private String headOfficeName;
    private String headOfficeAddress;
    private String headOfficePhone;
    private String headOfficeEmail;
    private List<SubOfficeResponse> subOffices;
    
    public static ContactUsResponse fromEntity(List<ContactUsFeature> offices) {
    	ContactUsFeature headOffice = offices.stream()
                .filter(office -> office.getOfficeType() == OfficeType.HEAD_OFFICE)
                .findFirst()
                .orElse(null);

        List<SubOfficeResponse> subOffices = offices.stream()
                .filter(office -> office.getOfficeType() == OfficeType.SUB_OFFICE)
                .map(SubOfficeResponse::fromEntity)
                .toList();

        return ContactUsResponse.builder()
                .headOfficeName(headOffice != null ? headOffice.getOfficeName() : "No Head Office")
                .headOfficeAddress(headOffice != null ? headOffice.getAddress() : "N/A")
                .headOfficePhone(headOffice != null ? headOffice.getPhoneNumber() : "N/A")
                .headOfficeEmail(headOffice != null ? headOffice.getEmail() : "N/A")
                .subOffices(subOffices)
                .build();
    }
}
