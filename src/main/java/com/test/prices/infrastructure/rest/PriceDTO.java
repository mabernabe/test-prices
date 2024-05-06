package com.test.prices.infrastructure.rest;

import com.test.prices.domain.Price;

public class PriceDTO {
	
	private String productId;
	
	private Integer brandId;
	
	private Integer priceId;
	
	private DateIntervalDTO applicationDate;
	
	private float price;

	
	public PriceDTO() {
		super();
	}


	public PriceDTO(Price price) {
		this.productId = price.getProductId();
		this.brandId = price.getBrandId();
		this.priceId = price.getId();
		this.applicationDate = new DateIntervalDTO(price.getStartDate(), price.getEndDate());
		this.price = price.getPrice();
	}


	public String getProductId() {
		return productId;
	}


	public Integer getBrandId() {
		return brandId;
	}

	

	public Integer getPriceId() {
		return priceId;
	}


	public DateIntervalDTO getApplicationDate() {
		return applicationDate;
	}


	public float getPrice() {
		return price;
	}

}
