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
@Table(name = "galleryFeatures")
public class GalleryFeature extends AbstractEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "key", nullable = false)
	@SequenceGenerator(name = "gallery_feature_seq", sequenceName = "gallery_feature_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "gallery_feature_seq")
	private Long key;
	
	@Column(nullable = false, length = 3000)
	private String imageName;
	
	@Column(name="altImageName",length = 3000)
	private String altImageName;
	
	private Long displaySeq;
	
	private String galleryType;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "WEBSITEDETAIL_ID", referencedColumnName = "key")
	private WebSiteDetails webSiteDetails;
	 
}