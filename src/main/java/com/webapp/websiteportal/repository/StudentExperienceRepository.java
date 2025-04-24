package com.webapp.websiteportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.websiteportal.entity.AcademicDetail;
import com.webapp.websiteportal.entity.StudentExperience;
import com.webapp.websiteportal.entity.StudentPortfolio;

@Repository
public interface StudentExperienceRepository extends JpaRepository<StudentExperience, Long> {

	 List<StudentExperience> findByStudentPortfolioAndIsActive(StudentPortfolio studentPortfolio,Character isActive);
}
