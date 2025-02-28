package com.webapp.websiteportal.entity;

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
@Table(name = "academic_details")
public class AcademicDetail extends AbstractEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "key", nullable = false)
	@SequenceGenerator(name = "academic_details_seq", sequenceName = "academic_details_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "academic_details_seq")
	private Long key;
	
	private String degree; // 10th, 12th, Bachelor, Master, etc.
    private String institution;
    private Date passingYear;
    private Double percentage; // Store percentage or GPA
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_portfolio_id", referencedColumnName = "key")
    private StudentPortfolio studentPortfolio;
    
}