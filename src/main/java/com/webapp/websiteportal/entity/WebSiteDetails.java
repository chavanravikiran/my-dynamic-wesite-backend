package com.webapp.websiteportal.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "websiteDetails")
public class WebSiteDetails extends AbstractEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "key", nullable = false)
	@SequenceGenerator(name = "websiteDetails_seq", sequenceName = "websiteDetails_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "websiteDetails_seq")
	private Long key;
	
	@Column(nullable = false, length = 3000)
	private String websiteName;
	
	@Column(nullable = false, length = 3000)
	private String websiteNameMr;
	
	@Column(length = 3000)
	private String websiteNameHi;
	
	private String oldWebsiteLink;

	private String websiteLogo;
	
//	private String latitude;
//	
//	private String longitude;
//	
//	@Column(name="address",length = 3000)
//	private String address;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private WebSiteType webSiteType;
	 
}
