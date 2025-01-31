package com.webapp.websiteportal.dto;

import java.util.List;

import com.webapp.websiteportal.entity.ContactUsFeature;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubOfficeResponse {

	private String officeName;
    private String address;
    private String phoneNumber;
    private String email;
    
    public static SubOfficeResponse fromEntity(ContactUsFeature contact) {
        return SubOfficeResponse.builder()
                .officeName(contact.getOfficeName())
                .address(contact.getAddress())
                .phoneNumber(contact.getPhoneNumber())
                .email(contact.getEmail())
                .build();
    }
    
}
