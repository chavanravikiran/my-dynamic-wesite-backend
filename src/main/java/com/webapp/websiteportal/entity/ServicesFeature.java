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
@Table(name = "serviceFeature")
public class ServicesFeature extends AbstractEntity{

	private static final long serialVersionUID = 8784404167292074929L;

	@Id
	@Column(name = "key", nullable = false)
	@SequenceGenerator(name = "service_feature_seq", sequenceName = "service_feature_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "service_feature_seq")
	private Long key;
	
	@Column(nullable = false, length = 3000)
	private String serviceName;
	
	@Column(name="serviceDetail",length = 20000)
	private String serviceDetail;
	
	@Column(name="serviceClickRouting",length = 3000)
	private String serviceClickRouting;
	
	@Column(name="serviceLogo", length = 3000)
	private String serviceLogo;
	
	@Column(name="display_seq")
	private Long displaySeq;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "WEBSITEDETAIL_ID", referencedColumnName = "key")
	private WebSiteDetails webSiteDetails;
	
}