package com.webapp.websiteportal.mapper;

import org.springframework.stereotype.Component;

import com.webapp.websiteportal.dto.TransactionDTO;
import com.webapp.websiteportal.entity.Transaction;

@Component
public class TransactionMapper {

    public TransactionDTO toDto(Transaction transaction) {
        return new TransactionDTO(transaction);
    }

}
