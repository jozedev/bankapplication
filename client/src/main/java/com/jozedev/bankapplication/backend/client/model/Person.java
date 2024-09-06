package com.jozedev.bankapplication.backend.client.model;

import jakarta.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class Person extends Base {
	private String name;
	private String dni;
	private String gender;
	private int age;
	private String address;
	private String phone;
}
