package com.ozaytunctan.ds.h2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class Person {

	@Id
	private Integer id;
	
	@NotNull
	@Column(name="Name",nullable=false)
	private String name;

	public Person() {
		// TODO Auto-generated constructor stub
	}
	public Person(Integer id, @NotNull String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
