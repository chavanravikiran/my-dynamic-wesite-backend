package com.webapp.websiteportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.websiteportal.dto.FeatureMenuResponse;
import com.webapp.websiteportal.service.IWebsiteRoleFeatureService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FeatureAccessController {

	@Autowired
    private IWebsiteRoleFeatureService featureService;

    @GetMapping("/websiteRoleUserFeature/{userId}")
    public List<FeatureMenuResponse> getFeaturesByUser(@PathVariable Long userId) throws Exception {
        List<FeatureMenuResponse> features = featureService.getUserWiseFeatures(userId);
        return features;
    }
}
