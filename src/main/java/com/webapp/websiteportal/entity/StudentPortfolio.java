package com.webapp.websiteportal.entity;

import java.util.Collection;

import com.webapp.websiteportal.dto.AcademicDetailModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "student_portfolio")
public class StudentPortfolio extends AbstractEntity{

	private static final long serialVersionUID = -6424869988749791129L;

	@Id
	@Column(name = "key", nullable = false)
	@SequenceGenerator(name = "student_portfolio_seq", sequenceName = "student_portfolio_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "student_portfolio_seq")
	private Long key;
	
	@Column(length = 3000)
	private String studentName;
	
	@Column(name="selfPhoto")
	private String selfPhoto;
	
	@Column(name="age")
	private Long age;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "WEBSITEDETAIL_ID", referencedColumnName = "key")
    private WebSiteDetails webSiteDetails;
	
}