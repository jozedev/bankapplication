package com.devsu.hackerearth.backend.account.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

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
