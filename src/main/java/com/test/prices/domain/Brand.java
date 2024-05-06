package com.test.prices.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BRANDS")
public class Brand {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer id;
	
	@Enumerated(EnumType.STRING)
	private BrandEnum name;

	public Brand() {
		super();
	}

	public Brand(BrandEnum name) {
		super();
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public BrandEnum getName() {
		return name;
	}

}
