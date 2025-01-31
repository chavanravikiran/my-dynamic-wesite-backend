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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contact_us_features")
public class ContactUsFeature extends AbstractEntity{
	
	private static final long serialVersionUID = 3910075015667875353L;

	@Id
	@Column(name = "key", nullable = false)
	@SequenceGenerator(name = "contact_us_features_seq", sequenceName = "contact_us_features_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "contact_us_features_seq")
	private Long key;
	
	@Column(name="officeName",length = 3000)
    private String officeName;
	
	@Column(nullable = false, length = 3000)
    private String address;
	
	private String latitude;
	
	private String longitude;
	
	private Long displaySeq;
	
	@Column(length = 10)
    private String phoneNumber;
	
	@Column(length = 255)
	private String email;
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OfficeType officeType; 
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "WEBSITEDETAIL_ID", referencedColumnName = "key")
	private WebSiteDetails webSiteDetails;
	 
}