package com.jozedev.bankapplication.backend.account.model.dto;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionDto {

	private Long id;
    private LocalDateTime date;
	private String type;
	private double amount;
	private Long accountId;
}
