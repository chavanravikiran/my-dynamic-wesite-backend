package com.webapp.websiteportal.dto;

import java.util.Date;
import java.util.List;

import com.webapp.websiteportal.entity.StudentExperience;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentExperienceModel {
	private Long key;
    private String companyName;
    private String projectDescription;
    private String technology;
    private Date startDateOnWorkingProject;
    private Date endDateOnWorkingProject;
    private List<ProjectModel> projects;
    
    public StudentExperienceModel(StudentExperience experience) {
        this.key = experience.getKey();
        this.companyName = experience.getCompanyName();
        this.projectDescription = experience.getProjectDescription();
        this.technology = experience.getTechnology();
        this.startDateOnWorkingProject = experience.getStartDateOnWorkingProject();
        this.endDateOnWorkingProject = experience.getEndDateOnWorkingProject();
    }
}