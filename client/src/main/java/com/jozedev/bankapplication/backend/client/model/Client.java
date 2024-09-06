package com.jozedev.bankapplication.backend.client.model;

import jakarta.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Client extends Person {
	private String password;
	private boolean isActive;
}
