package com.webapp.websiteportal.entity;

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
@Table(name = "company_portfolio")
public class CompanyPortfolio extends AbstractEntity{

	private static final long serialVersionUID = -3416920452614078199L;

	@Id
	@Column(name = "key", nullable = false)
	@SequenceGenerator(name = "company_portfolio_seq", sequenceName = "company_portfolio_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "company_portfolio_seq")
	private Long key;
	
	@Column(length = 3000)
	private String companyName;
	
//	@OneToMany(mappedBy = "companyPortfolio", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<CompanyAchievement> achievements;
//
//    @OneToMany(mappedBy = "companyPortfolio", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<CompanySuccessStory> successStories;

    @OneToMany(mappedBy = "companyPortfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects;
}