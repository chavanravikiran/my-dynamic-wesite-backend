package com.webapp.websiteportal.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.webapp.websiteportal.entity.ServicesFeature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponseDTO {
	
    private Long key;
    private String serviceName;
    private String serviceDetail;
    private String serviceLogo;
    private String serviceClickRouting;
    
    public static List<ServiceResponseDTO> init(List<ServicesFeature> services) {
        return services.stream()
            .map(service -> new ServiceResponseDTO(
                service.getKey(),
                service.getServiceName(),
                service.getServiceDetail(),
                service.getServiceLogo(),
                service.getServiceClickRouting()
            ))
            .collect(Collectors.toList());
    }
}