package com.webapp.websiteportal.dto;

import com.webapp.websiteportal.entity.Skill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillModel {
    private Long key;
    private String skillName;
    private String proficiencyLevel;
    private Double percentage;

    public SkillModel(Skill skill) {
        this.key = skill.getKey();
        this.skillName = skill.getSkillName();
        this.proficiencyLevel = skill.getProficiencyLevel().name();
        this.percentage = skill.getPercentage();
    }
}
