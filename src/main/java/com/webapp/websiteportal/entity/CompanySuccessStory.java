package com.webapp.websiteportal.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "company_success_stories")
public class CompanySuccessStory extends AbstractEntity{

	private static final long serialVersionUID = -1808569568223089359L;

	@Id
	@Column(name = "key", nullable = false)
	@SequenceGenerator(name = "company_success_stories_seq", sequenceName = "company_success_stories_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "company_success_stories_seq")
	private Long key;
	
	private String storyTitle;
    
	@Column(length = 5000) // Longer description
    private String storyDescription;
    private Date completionDate; // When the project/story was completed
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_portfolio_id", referencedColumnName = "key")
    private CompanyPortfolio companyPortfolio;
    
    
}