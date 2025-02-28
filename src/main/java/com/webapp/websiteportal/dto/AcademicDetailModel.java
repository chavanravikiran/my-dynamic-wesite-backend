package com.webapp.websiteportal.dto;

import java.util.Date;

import com.webapp.websiteportal.entity.AcademicDetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcademicDetailModel {
    private Long key;
    private String degree;
    private String institution;
    private Date passingYear;
    private Double percentage;

    public AcademicDetailModel(AcademicDetail academicDetail) {
        this.key = academicDetail.getKey();
        this.degree = academicDetail.getDegree();
        this.institution = academicDetail.getInstitution();
        this.passingYear = academicDetail.getPassingYear();
        this.percentage = academicDetail.getPercentage();
    }
}