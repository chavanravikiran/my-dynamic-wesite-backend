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
@Table(name = "skill")
public class Skill extends AbstractEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "key", nullable = false)
	@SequenceGenerator(name = "skill_seq", sequenceName = "skill_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "skill_seq")
	private Long key;
	
	private String skillName; // e.g., Java, PHP, Angular, etc.

    @Enumerated(EnumType.STRING)
    private ProficiencyLevel proficiencyLevel; // BEGINNER, INTERMEDIATE, ADVANCED

    private Double percentage; // Skill proficiency percentage (e.g., 90%)
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_portfolio_id", referencedColumnName = "key")
    private StudentPortfolio studentPortfolio;
}