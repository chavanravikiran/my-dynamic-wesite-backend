package com.webapp.websiteportal.service;

import com.webapp.websiteportal.entity.Account;
import com.webapp.websiteportal.entity.Users;

public interface AccountService {

	public Account createAccount(Users user);
	public boolean isPinCreated(String accountNumber) ;
	public void createPin(String accountNumber, String password, String pin) ;
	public void updatePin(String accountNumber, String oldPIN, String password, String newPIN);
	public void cashDeposit(String accountNumber, String pin, double amount);
	public void cashWithdrawal(String accountNumber, String pin, double amount);
	public void fundTransfer(String sourceAccountNumber, String targetAccountNumber, String pin, double amount);
	public boolean doesPinExist(String accountNumber);
	
	
}
