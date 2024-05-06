package com.test.prices;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;


import com.test.prices.domain.Brand;
import com.test.prices.domain.BrandEnum;
import com.test.prices.domain.Currency;
import com.test.prices.domain.Price;
import com.test.prices.infrastructure.rest.DateIntervalDTO;
import com.test.prices.infrastructure.rest.PriceDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TestApplicationTests {
	
	private static final String PRODUCT_ID = "35455";
	
	private static final String PRICE_NOT_FOUND_ERR_MSG = "Price not found";
	
	@LocalServerPort
	private int port;
	
	@Value("${prices.uri}")
	private String pricesURI;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	void contextLoads() {
	}
	
	@ParameterizedTest
	@MethodSource("uriParamsAndExpectedPrices")
	public void When_RequestAPrice_Expect_RightPriceInResponse(Map<String, String> uriParams, Price expectedPrice) throws Exception {
		PriceDTO priceDTO = this.restTemplate.getForObject(buildURL(), PriceDTO.class, uriParams);
		checkPrice(priceDTO, expectedPrice);
	}
	
	private String buildURL() {
		String uriParams= "productId={productId}";
		uriParams+= "&applicationDate={applicationDate}";
		uriParams+= "&brandId={brandId}";
		return "http://localhost:" + port + pricesURI + "/" + "price" + "?" + uriParams; 
	}
	
	private static Stream<Arguments> uriParamsAndExpectedPrices() throws ParseException {
		return Stream.of(
	    		Arguments.of(buildUriParams(PRODUCT_ID, "2020-06-14-10:00:00", "1"), buildPrice("2020-06-14-00:00:00", "2020-12-31-23:59:59", 0, 35.50f)),
	    		Arguments.of(buildUriParams(PRODUCT_ID, "2020-06-14-16:00:00", "1"), buildPrice("2020-06-14-15:00:00", "2020-06-14-18:30:00", 1, 25.45f)),
	    		Arguments.of(buildUriParams(PRODUCT_ID, "2020-06-14-21:00:00", "1"), buildPrice("2020-06-14-00:00:00", "2020-12-31-23:59:59", 0, 35.50f)),
	    		Arguments.of(buildUriParams(PRODUCT_ID, "2020-06-15-10:00:00", "1"), buildPrice("2020-06-15-00:00:00", "2020-06-15-11:00:00", 1, 30.50f)),
	    		Arguments.of(buildUriParams(PRODUCT_ID, "2020-06-16-21:00:00", "1"), buildPrice("2020-06-15-16:00:00", "2020-12-31-23:59:59", 1, 38.95f))
	    );
	}
	
	private static Price buildPrice(String startDate, String endDate, int priority, float price) throws ParseException {
		SimpleDateFormat dateFormat = DateIntervalDTO.datesFormat;
		return new Price(new Brand(BrandEnum.ZARA), PRODUCT_ID, dateFormat.parse(startDate), dateFormat.parse(endDate), price, Currency.EUR, priority);
	}


	private static Map<String, String> buildUriParams(String productId, String applicationDate, String brandId) {
		Map<String, String> uriParams = new HashMap<String, String>();
		uriParams.put("productId", productId);
		uriParams.put("applicationDate", applicationDate);
		uriParams.put("brandId", brandId);
		return uriParams;
	}
	
	private void checkPrice(PriceDTO priceDTO, Price expectedPrice) throws ParseException {
		assertThat(priceDTO.getProductId()).isEqualTo(expectedPrice.getProductId());
		assertThat(priceDTO.getPrice()).isEqualTo(expectedPrice.getPrice());
		SimpleDateFormat dateFormat = DateIntervalDTO.datesFormat;
		Date applicationStartDate = dateFormat.parse(priceDTO.getApplicationDate().getStartDate());
		assertThat(applicationStartDate.compareTo(expectedPrice.getStartDate())).isZero();
		Date applicationEndDate = dateFormat.parse(priceDTO.getApplicationDate().getEndDate());
		assertThat(applicationEndDate.compareTo(expectedPrice.getEndDate())).isZero();
	}
	
	@Test()
	public void When_RequestPriceOutOfApplicationDate_Expect_NoPriceFoundResponse() throws Exception {
		String error = this.restTemplate.getForObject(buildURL(), String.class, buildUriParams(PRODUCT_ID,"2024-06-14-10:00:00", "5"));
		assertThat(error.equals(PRICE_NOT_FOUND_ERR_MSG)).isTrue();
	}
	
	@Test()
	public void When_RequestPriceForNotExistingProduct_Expect_NoPriceFoundResponse() throws Exception {
		String error = this.restTemplate.getForObject(buildURL(), String.class, buildUriParams("22122","2024-06-14-10:00:00", "5"));
		assertThat(error.equals(PRICE_NOT_FOUND_ERR_MSG)).isTrue();
	}
	
	@Test()
	public void When_RequestInvalidApplicationDateFormat_Expect_NoPriceFoundResponse() throws Exception {
		String error = this.restTemplate.getForObject(buildURL(), String.class, buildUriParams(PRODUCT_ID,"45-14-10:00:00", "5"));
		assertThat(error.equals("Unparseable date: \"45-14-10:00:00\"")).isTrue();
	}
	
}
