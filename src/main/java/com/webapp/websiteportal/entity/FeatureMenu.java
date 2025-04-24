package com.webapp.websiteportal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "feature_menu")
public class FeatureMenu extends AbstractEntity{

	private static final long serialVersionUID = 879853111701972210L;
	
	@Id
	@Column(name = "key", nullable = false)
	@SequenceGenerator(name = "feature_menu_seq", sequenceName = "feature_menu_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "feature_menu_seq")
	private Long key;
	
	@Column(nullable = false, length = 3000)
	private String featureName;

	@Column(nullable = false, length = 3000)
	private String featureNameMr;
	
	private String featureIcon;
	
	@Column(name="display_seq")
	private Long displaySeq;
    
}
