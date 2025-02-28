package com.webapp.websiteportal.dto;

import com.webapp.websiteportal.entity.Project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectModel {
    private Long key;
    private String projectName;
    private String description;

    public ProjectModel(Project project) {
        this.key = project.getKey();
        this.projectName = project.getProjectName();
        this.description = project.getDescription();
    }
}
