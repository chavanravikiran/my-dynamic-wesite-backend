package com.webapp.websiteportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.websiteportal.entity.AcademicDetail;
import com.webapp.websiteportal.entity.DevelopedType;
import com.webapp.websiteportal.entity.Project;
import com.webapp.websiteportal.entity.Skill;
import com.webapp.websiteportal.entity.StudentExperience;
import com.webapp.websiteportal.entity.StudentPortfolio;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
	 List<Project> findByStudentPortfolio(StudentPortfolio studentPortfolio);
	 
	 List<Project> findByStudentExperienceAndDevelopedType(StudentExperience experience, DevelopedType developedType);
	 
	 List<Project> findByStudentPortfolioAndIsActiveAndDevelopedType(StudentPortfolio studentPortfolio, Character isActive,
			DevelopedType academic);

	List<Project> findByStudentExperienceAndIsActiveAndDevelopedType(StudentExperience studentPortfolio, Character isActive,
			DevelopedType experience);

}
