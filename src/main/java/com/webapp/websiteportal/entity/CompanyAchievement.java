package com.webapp.websiteportal.entity;

import java.time.LocalDate;
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
@Table(name = "company_achievement")
public class CompanyAchievement extends AbstractEntity{

	private static final long serialVersionUID = -9023041391129524194L;

	@Id
	@Column(name = "key", nullable = false)
	@SequenceGenerator(name = "company_achievement_seq", sequenceName = "company_achievement_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "company_achievement_seq")
	private Long key;
	
	private String achievementTitle;
    private String achievementDescription;
    private LocalDate achievementDate; // Date of achievement

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_portfolio_id", referencedColumnName = "key")
    private CompanyPortfolio companyPortfolio;
}