package com.test.prices.domain;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "PRICES")
public class Price {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer id;
	
	@Enumerated
	@ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="BRAND_ID", referencedColumnName="ID")
	private Brand brand;
	
	@Column(name = "PRODUCT_ID")
	private String productId;
	
	@Column(name = "START_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "PRICE")
	private float price;
	
	@Column(name = "CURRENCY")
	@Enumerated(EnumType.STRING)
	private Currency currency;
	
	@Column(name = "PRIORITY")
	private int priority;

	public Price() {
		super();
	}

	public Price(Brand brand, String productId, Date startDate, Date endDate, float price,
			Currency currency, int priority) {
		super();
		this.brand = brand;
		this.productId = productId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
		this.currency = currency;
		this.priority = priority;
	}

	public Integer getId() {
		return id;
	}

	public Brand getBrand() {
		return brand;
	}

	public String getProductId() {
		return productId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public float getPrice() {
		return price;
	}

	public Currency getCurrency() {
		return currency;
	}

	public int getPriority() {
		return priority;
	}

	public Integer getBrandId() {
		return brand.getId();
	}
	
	

}
