package com.webapp.websiteportal.entity;

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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
@Table(name = "inquiry")
public class Inquiry extends AbstractEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "key", nullable = false)
	@SequenceGenerator(name = "inquiry_seq", sequenceName = "inquiry_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "inquiry_seq")
	private Long key;
	
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z]+$")
	private String firstName;
	
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z]+$")
	private String lastName;
	
	@NotBlank
    @Email
    private String email;
	
//	@NotBlank
    @Pattern(regexp = "^[0-9]{10}$")
    private String phone;
	
	@NotBlank
	@Column(name="title")
	private String title;
	
	@NotBlank
    @Size(min = 10)
	@Column(name="message",length = 3000)
    private String message;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "WEBSITEDETAIL_ID", referencedColumnName = "key")
	private WebSiteDetails webSiteDetails;
	 
}