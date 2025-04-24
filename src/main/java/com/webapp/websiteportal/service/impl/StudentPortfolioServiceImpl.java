package com.webapp.websiteportal.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.websiteportal.dto.AcademicDetailModel;
import com.webapp.websiteportal.dto.LanguageModel;
import com.webapp.websiteportal.dto.PortfolioModel;
import com.webapp.websiteportal.dto.ProjectModel;
import com.webapp.websiteportal.dto.SkillModel;
import com.webapp.websiteportal.dto.StudentExperienceModel;
import com.webapp.websiteportal.entity.AcademicDetail;
import com.webapp.websiteportal.entity.DevelopedType;
import com.webapp.websiteportal.entity.Language;
import com.webapp.websiteportal.entity.Project;
import com.webapp.websiteportal.entity.Skill;
import com.webapp.websiteportal.entity.StudentExperience;
import com.webapp.websiteportal.entity.StudentPortfolio;
import com.webapp.websiteportal.entity.WebSiteDetails;
import com.webapp.websiteportal.repository.AcademicDetailRepository;
import com.webapp.websiteportal.repository.LanguageRepository;
import com.webapp.websiteportal.repository.ProjectRepository;
import com.webapp.websiteportal.repository.SkillRepository;
import com.webapp.websiteportal.repository.StudentExperienceRepository;
import com.webapp.websiteportal.repository.StudentPortfolioRepository;
import com.webapp.websiteportal.service.IStudentPortfolioService;

@Service
public class StudentPortfolioServiceImpl implements IStudentPortfolioService{

	@Autowired
    private StudentPortfolioRepository studentPortfolioRepository;

    @Autowired
    private AcademicDetailRepository academicDetailRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private StudentExperienceRepository studentExperienceRepository;
    
    @Override
	public StudentPortfolio findByWebSiteDetails(WebSiteDetails webSiteDetails) {
		return studentPortfolioRepository.findByWebSiteDetailsAndIsActive(webSiteDetails,'Y');
	}
    
    
    public PortfolioModel getPortfolioById(Long studentId) {
        // Fetch StudentPortfolio
        StudentPortfolio studentPortfolio = studentPortfolioRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Initialize PortfolioModel
        PortfolioModel portfolioModel = new PortfolioModel().init(studentPortfolio);

        // Fetch and Set Academic Details
        List<AcademicDetail> academicDetails = academicDetailRepository.findByStudentPortfolio(studentPortfolio);
        portfolioModel.setAcademicDetail(
                academicDetails.stream().map(AcademicDetailModel::new).collect(Collectors.toList()));

        // Fetch and Set Skills
        List<Skill> skills = skillRepository.findByStudentPortfolio(studentPortfolio);
        portfolioModel.setSkill(
                skills.stream().map(SkillModel::new).collect(Collectors.toList()));

        // Fetch and Set Projects
        List<Project> projects = projectRepository.findByStudentPortfolioAndIsActiveAndDevelopedType(studentPortfolio,'Y',DevelopedType.ACADEMIC);
        portfolioModel.setProject(
                projects.stream().map(ProjectModel::new).collect(Collectors.toList()));

        // Fetch and Set Languages
        List<Language> languages = languageRepository.findByStudentPortfolio(studentPortfolio);
        portfolioModel.setLanguage(
                languages.stream().map(LanguageModel::new).collect(Collectors.toList()));

        List<StudentExperience> experiences = studentExperienceRepository.findByStudentPortfolioAndIsActive(studentPortfolio, 'Y');
        
        List<StudentExperienceModel> experienceModels = experiences.stream().map(experience -> {
            StudentExperienceModel experienceModel = new StudentExperienceModel(experience);

            // Fetch only the projects that belong to this specific experience
            List<Project> experienceProjects = projectRepository.findByStudentExperienceAndIsActiveAndDevelopedType(experience,'Y',DevelopedType.EXPERIENCE);

            // Convert projects to model and set them in the experience model
            List<ProjectModel> projectModels = experienceProjects.stream()
                    .map(ProjectModel::new)
                    .collect(Collectors.toList());
            experienceModel.setProjects(projectModels);

            return experienceModel;
        }).collect(Collectors.toList());

        portfolioModel.setExperiences(experienceModels);
        return portfolioModel;
    }
}
