package com.test.prices.infrastructure.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.test.prices.application.PricesRepositoryPort;
import com.test.prices.domain.Price;

@Repository
public interface PricesJPAAdapter extends PricesRepositoryPort, JpaRepository<Price, Integer> {
	
	@Query(value = "SELECT * FROM PRICES p where :applicationDate BETWEEN p.START_DATE AND p.END_DATE "
			+ "AND :productId = p.PRODUCT_ID AND :brandId = p.BRAND_ID "
			+ "ORDER BY p.PRIORITY DESC LIMIT 1", nativeQuery = true)
	public Price findPrice( @Param("productId")String productId, @Param("applicationDate")Date applicationDate,  @Param("brandId")String brandId);

}



