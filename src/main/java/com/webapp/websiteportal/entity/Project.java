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
@Table(name = "project")
public class Project extends AbstractEntity{

	private static final long serialVersionUID = 205954098666422271L;

	@Id
	@Column(name = "key", nullable = false)
	@SequenceGenerator(name = "project_seq", sequenceName = "project_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "project_seq")
	private Long key;
	
	private String projectName;
	private String description;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "student_portfolio_id", referencedColumnName = "key")
	private StudentPortfolio studentPortfolio;

	@ManyToOne
    @JoinColumn(name = "company_portfolio_id")
    private CompanyPortfolio companyPortfolio;
}