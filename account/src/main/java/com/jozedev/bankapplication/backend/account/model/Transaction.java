package com.jozedev.bankapplication.backend.account.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Transaction extends Base {

	private LocalDateTime date;
	private String type;
	private double amount;

	@Column(name = "account_id")
	private Long accountId;
}
