package com.webapp.websiteportal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "website_feature_menu")
public class WebsiteFeatureMenu extends AbstractEntity{

	private static final long serialVersionUID = 879853111701972210L;
	
	@Id
	@Column(name = "key", nullable = false)
	@SequenceGenerator(name = "website_feature_menu_seq", sequenceName = "website_feature_menu_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "website_feature_menu_seq")
	private Long key;
	
	@Column(name="display_seq")
	private Long displaySeq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "website_details_key")
	private WebSiteDetails websiteDetails;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "feature_menu_key")
	private FeatureMenu featureMenu;
}
