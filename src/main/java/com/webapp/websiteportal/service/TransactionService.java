package com.webapp.websiteportal.service;

import java.util.List;

import com.webapp.websiteportal.dto.TransactionDTO;

public interface TransactionService {

	List<TransactionDTO> getAllTransactionsByAccountNumber(String accountNumber);

}
