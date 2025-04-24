package com.webapp.websiteportal.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.webapp.websiteportal.entity.StudentPortfolio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioModel {

	private Long key;
    private String studentName;
//    private byte[] selfPhoto;
    private Long age;
    private String websiteName;
    private Long websiteNameId;
    
    private List<AcademicDetailModel> academicDetail;
    private List<SkillModel> skill;
    private List<ProjectModel> project;
    private List<LanguageModel> language;
    private List<StudentExperienceModel> experiences;
    
    public PortfolioModel init(StudentPortfolio studentPortfolio) {
        this.key = studentPortfolio.getKey();
        this.studentName = studentPortfolio.getStudentName();
//        this.selfPhoto = studentPortfolio.getSelfPhoto();
        this.age = studentPortfolio.getAge();

        if (studentPortfolio.getWebSiteDetails() != null) {
            this.websiteName = studentPortfolio.getWebSiteDetails().getWebsiteName();
            this.websiteNameId = studentPortfolio.getWebSiteDetails().getKey();
        }

        this.academicDetail = new ArrayList<>();
        this.skill = new ArrayList<>();
        this.project = new ArrayList<>();
        this.language = new ArrayList<>();

        this.experiences = new ArrayList<>();
        return this;
    }
}
