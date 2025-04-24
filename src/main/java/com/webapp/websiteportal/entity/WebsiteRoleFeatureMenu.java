package com.webapp.websiteportal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "website_role_feature_menu")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebsiteRoleFeatureMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "website_role_feature_menu_seq")
    @SequenceGenerator(name = "website_role_feature_menu_seq", sequenceName = "website_role_feature_menu_seq", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "website_details_id", nullable = false)
    private WebSiteDetails websiteDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feature_menu_id", nullable = false)
    private FeatureMenu featureMenu;
}