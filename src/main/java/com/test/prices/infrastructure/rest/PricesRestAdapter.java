package com.test.prices.infrastructure.rest;

import java.text.ParseException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.prices.application.PriceNotFoundException;
import com.test.prices.application.PricesPort;
import com.test.prices.domain.Price;

@RestController
@RequestMapping("${prices.uri}")
public class PricesRestAdapter {
	
	@Autowired
	private PricesPort pricesPort;
	
	@RequestMapping(value="/price", method = RequestMethod.GET)
	public PriceDTO getPrice(@Valid @NotEmpty @RequestParam String productId, @Valid @NotEmpty @RequestParam  String applicationDate, @RequestParam @Valid @NotEmpty String brandId) throws ParseException, PriceNotFoundException {
		Price price = pricesPort.getPrice(productId, DateIntervalDTO.datesFormat.parse(applicationDate), brandId);
		return new PriceDTO(price);
	}

}
