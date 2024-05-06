package com.test.prices.infrastructure.rest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateIntervalDTO {
	
	public static final SimpleDateFormat datesFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");

	private String startDate;
	
	private String endDate;
	

	public DateIntervalDTO() {
		super();
	}

	public DateIntervalDTO(Date startDate, Date endDate) {
		super();
		this.startDate = datesFormat.format(startDate);
		this.endDate = datesFormat.format(endDate);
	}

	public String getStartDate() {
		return startDate;
	}


	public String getEndDate() {
		return endDate;
	}


}
