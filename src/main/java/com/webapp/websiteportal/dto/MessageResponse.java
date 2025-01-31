package com.webapp.websiteportal.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse implements Serializable{
	private static final long serialVersionUID = -2974579329091991055L;
	
	private ServiceStatus status;
	private String  errorMessage;
	private String  successMsg;
	
	public boolean isError() {
		return this.status.equals(ServiceStatus.FAILURE);
	}
	
	public boolean isSuccess() {
		return this.status.equals(ServiceStatus.SUCCESS);
	}
	
	public static MessageResponse error(String errorMessage) {
		return MessageResponse.builder().status(ServiceStatus.FAILURE).errorMessage(errorMessage).build();
	}

	public static MessageResponse success() {
		return MessageResponse.builder().status(ServiceStatus.SUCCESS).build();
	}
	
	public static MessageResponse successWithMsg(String successMsg) {
		return MessageResponse.builder().status(ServiceStatus.SUCCESS).successMsg(successMsg).build();
	}
	
	enum ServiceStatus {
		SUCCESS,
		FAILURE
	}
}
