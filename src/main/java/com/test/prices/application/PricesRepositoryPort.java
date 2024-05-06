package com.test.prices.application;

import java.util.Date;

import com.test.prices.domain.Price;

public interface PricesRepositoryPort {
	
	public Price findPrice( String productId, Date applicationDate,  String brandId);


}
