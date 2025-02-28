package com.webapp.websiteportal.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "language")
public class Language extends AbstractEntity{

	private static final long serialVersionUID = -2904344698479020250L;

	@Id
	@Column(name = "key", nullable = false)
	@SequenceGenerator(name = "language_seq", sequenceName = "language_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "language_seq")
	private Long key;
	
	@Enumerated(EnumType.STRING)
	private languageName languageName;
	
    private Boolean canRead;
    private Boolean canWrite;
    private Boolean canSpeak;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_portfolio_id", referencedColumnName = "key")
    private StudentPortfolio studentPortfolio;  
}