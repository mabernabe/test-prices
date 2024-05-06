package com.test.prices.application;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.prices.domain.Price;

@Service
public class PricesPort {
	
	@Autowired
	PricesRepositoryPort pricesRepositoryPort;

	public Price getPrice(String productId, Date applicationDate, String brandId) throws PriceNotFoundException {
		Price price = pricesRepositoryPort.findPrice(productId, applicationDate, brandId);
		if (price == null){
            throw new PriceNotFoundException();
        }
		return price;
	}

}
