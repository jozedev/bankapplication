package com.jozedev.bankapplication.backend.account.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDto {

	private Long id;
	private String number;
	private String type;
	private double initialAmount;
	private double balance;
	private boolean isActive;
	private Long clientId;
}
