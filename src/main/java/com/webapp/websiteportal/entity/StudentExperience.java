package com.webapp.websiteportal.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "student_experience")
public class StudentExperience extends AbstractEntity{

	private static final long serialVersionUID = -3893052691918601567L;

	@Id
	@Column(name = "key", nullable = false)
	@SequenceGenerator(name = "student_experience_seq", sequenceName = "student_experience_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "student_experience_seq")
	private Long key;
	
	@Column(length = 30000)
	private String companyName;
	
	private String projectDescription;
	
	private String technology;
	
	private Date startDateOnWorkingProject;
	
	private Date endDateOnWorkingProject;  // NULL means "till date"

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studentPortfolio_id", referencedColumnName = "key")
    private StudentPortfolio studentPortfolio;
    
    //Relationship with projects (One Experience → Many Projects)
    @OneToMany(mappedBy = "studentExperience", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Project> projects;
    
    
}