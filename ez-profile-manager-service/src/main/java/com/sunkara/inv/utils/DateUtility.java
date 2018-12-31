package com.sunkara.inv.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

@Component
public class DateUtility {

	public Date convertStringToDate(@NotNull String inDate, @NotNull String inDateFormat) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(inDateFormat);
		
		return sdf.parse(inDate);
	}

	public long getCurrentTimestampUTC() {
		Calendar utcCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		return utcCal.getTimeInMillis();
	}
}
