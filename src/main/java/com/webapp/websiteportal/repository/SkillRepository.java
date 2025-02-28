package com.webapp.websiteportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.websiteportal.entity.AcademicDetail;
import com.webapp.websiteportal.entity.Skill;
import com.webapp.websiteportal.entity.StudentPortfolio;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
	 List<Skill> findByStudentPortfolio(StudentPortfolio studentPortfolio);
}
