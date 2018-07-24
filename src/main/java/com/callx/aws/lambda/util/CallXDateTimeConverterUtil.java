package com.callx.aws.lambda.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.amazonaws.services.lambda.runtime.Context;
import com.callx.aws.lambda.handlers.Request;

public class CallXDateTimeConverterUtil {
	
	public static DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
	public static String AMERICA_TIMEZONE ="America/Los_Angeles";
	public static String UTC_TIMEZONE ="Etc/UTC";
	private static DateTimeFormatter dtf1 = DateTimeFormat.forPattern("MMM d, yyyy");
	
	public static String getStartOfDay(DateTime datetime, String srcTimeZone,String distTimeZone){
		 DateTime dt = new DateTime(datetime.getYear(), datetime.getMonthOfYear(), datetime.getDayOfMonth(), 00, 00,00, DateTimeZone.forID(srcTimeZone));
		 DateTime dtLos = dt.withZone(DateTimeZone.forID(distTimeZone));
		return dtf.print(dtLos);
	}
	
	public static String getEndOfDay(DateTime datetime, String srcTimeZone, String distTimeZone ){
		 DateTime dt = new DateTime(datetime.getYear(), datetime.getMonthOfYear(), datetime.getDayOfMonth(), 23, 59,59, DateTimeZone.forID(srcTimeZone));
		 DateTime dtLos = dt.withZone(DateTimeZone.forID(distTimeZone));
		return dtf.print(dtLos);
	}

	public static Object[] getArryOfCalObjects(String dayRef, String refFrom, String refTo){
		DateTime dt = new DateTime();
		DateTime srcDateTime = dt.toDateTime(DateTimeZone.forID(AMERICA_TIMEZONE)).withHourOfDay(0).withMinuteOfHour(00).withSecondOfMinute(00);
		String rangeOne =null, rangeTwo = null;
		int days = 0;
		if (dayRef.equalsIgnoreCase("today")) {
			rangeOne = getStartOfDay(srcDateTime, srcDateTime.getZone().getID(), UTC_TIMEZONE);
			rangeTwo = getEndOfDay(srcDateTime, srcDateTime.getZone().getID(), UTC_TIMEZONE);
			return new Object[]{rangeOne,rangeTwo,0,false};
		} else if (dayRef.equalsIgnoreCase("yesterday")) {
			srcDateTime=srcDateTime.minusDays(1);
			rangeOne = getStartOfDay(srcDateTime, srcDateTime.getZone().getID(), UTC_TIMEZONE);
			rangeTwo = getEndOfDay(srcDateTime, srcDateTime.getZone().getID(), UTC_TIMEZONE);
			return new Object[]{rangeOne,rangeTwo,0,false};
		} else if (dayRef.equalsIgnoreCase("day-before-yesterday")) {
			srcDateTime = srcDateTime.minusDays(2);
			rangeOne = getStartOfDay(srcDateTime, srcDateTime.getZone().getID(), UTC_TIMEZONE);
			rangeTwo = getEndOfDay(srcDateTime, srcDateTime.getZone().getID(), UTC_TIMEZONE);
			return new Object[]{rangeOne,rangeTwo,0,false};
		} else if (dayRef.equalsIgnoreCase("seven-days")) {
			srcDateTime = srcDateTime.minusDays(7);
			rangeOne = getStartOfDay(srcDateTime, srcDateTime.getZone().getID(), UTC_TIMEZONE);
			srcDateTime = srcDateTime.plusDays(6);
			rangeTwo = getEndOfDay(srcDateTime, srcDateTime.getZone().getID(), UTC_TIMEZONE);
			return new Object[]{rangeOne,rangeTwo,0,false};
		} else if (dayRef.equalsIgnoreCase("week-before-seven-days")) {
			srcDateTime = srcDateTime.minusDays(14);
			rangeOne = getStartOfDay(srcDateTime, srcDateTime.getZone().getID(), UTC_TIMEZONE);
			srcDateTime = srcDateTime.plusDays(6);
			rangeTwo = getEndOfDay(srcDateTime, srcDateTime.getZone().getID(), UTC_TIMEZONE);
			return new Object[]{rangeOne,rangeTwo,0,false};
		} else if (dayRef.equalsIgnoreCase("thirty-days")) {
			srcDateTime = srcDateTime.minusDays(30);
			rangeOne = getStartOfDay(srcDateTime, srcDateTime.getZone().getID(), UTC_TIMEZONE);
			srcDateTime = srcDateTime.plusDays(29);
			rangeTwo = getEndOfDay(srcDateTime, srcDateTime.getZone().getID(), UTC_TIMEZONE);
			
			return new Object[]{rangeOne,rangeTwo,0,false};
		} else if (dayRef.equalsIgnoreCase("month-before-thirty-days")) {
			srcDateTime = srcDateTime.minusDays(60);
			rangeOne = getStartOfDay(srcDateTime, srcDateTime.getZone().getID(), UTC_TIMEZONE);
			srcDateTime = srcDateTime.plusDays(29);
			rangeTwo = getEndOfDay(srcDateTime, srcDateTime.getZone().getID(), UTC_TIMEZONE);
			
			return new Object[]{rangeOne,rangeTwo,0,false};
		} else if (dayRef.equalsIgnoreCase("this-month")) {
			srcDateTime = srcDateTime.dayOfMonth().withMinimumValue();
			rangeOne = getStartOfDay(srcDateTime, srcDateTime.getZone().getID(), UTC_TIMEZONE);
			rangeTwo = getEndOfDay(new DateTime(), srcDateTime.getZone().getID(), UTC_TIMEZONE);
			
			return new Object[]{rangeOne,rangeTwo,0,false};
		} else if (dayRef.equalsIgnoreCase("last-month")) {
			srcDateTime = srcDateTime.minusMonths(1).dayOfMonth().withMinimumValue();
			rangeOne = getStartOfDay(srcDateTime, srcDateTime.getZone().getID(), UTC_TIMEZONE);
			srcDateTime = srcDateTime.dayOfMonth().withMaximumValue();
			rangeTwo = getEndOfDay(srcDateTime, srcDateTime.getZone().getID(), UTC_TIMEZONE);
			
			return new Object[]{rangeOne,rangeTwo,0,false};
		} else if (dayRef.equalsIgnoreCase("last-last-month")) {
			srcDateTime = srcDateTime.minusMonths(2).dayOfMonth().withMinimumValue();
			rangeOne = getStartOfDay(srcDateTime, srcDateTime.getZone().getID(), UTC_TIMEZONE);
			srcDateTime = srcDateTime.dayOfMonth().withMaximumValue();
			rangeTwo = getEndOfDay(srcDateTime, srcDateTime.getZone().getID(), UTC_TIMEZONE);
			
			return new Object[]{rangeOne,rangeTwo,0,false};
		}else if (dayRef.equalsIgnoreCase("this-year")) {
			srcDateTime = srcDateTime.dayOfYear().withMinimumValue();
			rangeOne = getStartOfDay(srcDateTime, srcDateTime.getZone().getID(), UTC_TIMEZONE);
			rangeTwo = getEndOfDay(new DateTime(), srcDateTime.getZone().getID(), UTC_TIMEZONE);
			return new Object[]{rangeOne,rangeTwo,0,false};
		} else if (dayRef.equalsIgnoreCase("last-year")) {
			srcDateTime = srcDateTime.minusYears(1).dayOfYear().withMinimumValue();
			rangeOne = getStartOfDay(srcDateTime, srcDateTime.getZone().getID(), UTC_TIMEZONE);
			srcDateTime = srcDateTime.dayOfYear().withMaximumValue();
			rangeTwo = getEndOfDay(srcDateTime, srcDateTime.getZone().getID(), UTC_TIMEZONE);
			return new Object[]{rangeOne,rangeTwo,0,false};
		} else if (dayRef.equalsIgnoreCase("custom")) {
			if(refFrom != null && refTo !=null){
				DateTime da = new DateTime();
				srcDateTime = dtf1.parseDateTime(refFrom);
				DateTime srcDateTime1 = dtf1.parseDateTime(refTo);
				if(da.getHourOfDay()<8){
					srcDateTime = srcDateTime.toDateTime(DateTimeZone.forID(AMERICA_TIMEZONE)).withHourOfDay(0).withMinuteOfHour(00).withSecondOfMinute(00);
					srcDateTime1 = srcDateTime1.toDateTime(DateTimeZone.forID(AMERICA_TIMEZONE)).withHourOfDay(0).withMinuteOfHour(00).withSecondOfMinute(00);
					rangeOne = getStartOfDay(srcDateTime.plusDays(1), srcDateTime.getZone().getID(), UTC_TIMEZONE);
					rangeTwo = getEndOfDay(srcDateTime1.plusDays(1), srcDateTime1.getZone().getID(), UTC_TIMEZONE);
					days = Days.daysBetween(srcDateTime, srcDateTime1).getDays();
				}else{
					srcDateTime = srcDateTime.toDateTime(DateTimeZone.forID(AMERICA_TIMEZONE)).withHourOfDay(0).withMinuteOfHour(00).withSecondOfMinute(00);
					srcDateTime1 = srcDateTime1.toDateTime(DateTimeZone.forID(AMERICA_TIMEZONE)).withHourOfDay(0).withMinuteOfHour(00).withSecondOfMinute(00);
					rangeOne = getStartOfDay(srcDateTime.plusDays(1), srcDateTime.getZone().getID(), UTC_TIMEZONE);
					rangeTwo = getEndOfDay(srcDateTime1.plusDays(1), srcDateTime1.getZone().getID(), UTC_TIMEZONE);
					days = Days.daysBetween(srcDateTime, srcDateTime1).getDays();
				}
			}
			return new Object[]{rangeOne,rangeTwo,days,false};
		} else if (dayRef.equalsIgnoreCase("previous-custom")) {
			if(refFrom != null && refTo !=null){
				DateTime da = new DateTime();
				srcDateTime = dtf1.parseDateTime(refFrom);
				DateTime srcDateTime1 = dtf1.parseDateTime(refTo);
				if(da.getHourOfDay()<8){
					srcDateTime = srcDateTime.toDateTime(DateTimeZone.forID(AMERICA_TIMEZONE)).withHourOfDay(0).withMinuteOfHour(00).withSecondOfMinute(00);;
					srcDateTime1 = srcDateTime1.toDateTime(DateTimeZone.forID(AMERICA_TIMEZONE)).withHourOfDay(0).withMinuteOfHour(00).withSecondOfMinute(00);;
					rangeOne = getStartOfDay(srcDateTime, srcDateTime.getZone().getID(), UTC_TIMEZONE);
					rangeTwo = getEndOfDay(srcDateTime1, srcDateTime1.getZone().getID(), UTC_TIMEZONE);
					days = Days.daysBetween(srcDateTime, srcDateTime1).getDays();
				}else {
					srcDateTime = srcDateTime.toDateTime(DateTimeZone.forID(AMERICA_TIMEZONE)).withHourOfDay(0).withMinuteOfHour(00).withSecondOfMinute(00);;
					srcDateTime1 = srcDateTime1.toDateTime(DateTimeZone.forID(AMERICA_TIMEZONE)).withHourOfDay(0).withMinuteOfHour(00).withSecondOfMinute(00);;
					days = Days.daysBetween(srcDateTime, srcDateTime1).getDays();
					rangeOne = getStartOfDay(srcDateTime, srcDateTime.getZone().getID(), UTC_TIMEZONE);
					rangeTwo = getEndOfDay(srcDateTime1, srcDateTime1.getZone().getID(), UTC_TIMEZONE);
				}
			}
			return new Object[]{rangeOne,rangeTwo,0,false};
		}
		return null;
	}
	
	public static List<DateTime> getDaysBetweenNew(String dayRef, String refFrom, String refTo){
		DateTime dt = new DateTime();
		DateTime srcDateTime = dt.toDateTime(DateTimeZone.forID(AMERICA_TIMEZONE)).withHourOfDay(0).withMinuteOfHour(00).withSecondOfMinute(00);;
		String rangeOne =null, rangeTwo = null;
		List<DateTime> listOfDays = new ArrayList<>();
		if (dayRef.equalsIgnoreCase("today")) {
			rangeOne = getStartOfDay(dt, srcDateTime.getZone().getID(), AMERICA_TIMEZONE);
			rangeTwo = getEndOfDay(dt, srcDateTime.getZone().getID(), AMERICA_TIMEZONE);
			//int days =Days.daysBetween(dtf.parseDateTime(rangeOne), dtf.parseDateTime(rangeTwo)).getDays();
			listOfDays.add(dtf.parseDateTime(rangeOne));
			return listOfDays;
		} else if (dayRef.equalsIgnoreCase("yesterday")) {
			srcDateTime=srcDateTime.minusDays(1);
			rangeOne = getStartOfDay(srcDateTime, srcDateTime.getZone().getID(), AMERICA_TIMEZONE);
			rangeTwo = getEndOfDay(srcDateTime, srcDateTime.getZone().getID(), AMERICA_TIMEZONE);
			//int days =Days.daysBetween(dtf.parseDateTime(rangeOne), dtf.parseDateTime(rangeTwo)).getDays();
			listOfDays.add(dtf.parseDateTime(rangeOne));
			return listOfDays;
		} else if (dayRef.equalsIgnoreCase("day-before-yesterday")) {
			srcDateTime = srcDateTime.minusDays(2);
			rangeOne = getStartOfDay(srcDateTime, srcDateTime.getZone().getID(), AMERICA_TIMEZONE);
			rangeTwo = getEndOfDay(srcDateTime, srcDateTime.getZone().getID(), AMERICA_TIMEZONE);
			//int days =Days.daysBetween(dtf.parseDateTime(rangeOne), dtf.parseDateTime(rangeTwo)).getDays();
			listOfDays.add(dtf.parseDateTime(rangeOne));
			return listOfDays;
		} else if (dayRef.equalsIgnoreCase("seven-days")) {
			srcDateTime = srcDateTime.minusDays(7);
			rangeOne = getStartOfDay(srcDateTime, srcDateTime.getZone().getID(), AMERICA_TIMEZONE);
			srcDateTime = srcDateTime.plusDays(7);
			rangeTwo = getEndOfDay(srcDateTime, srcDateTime.getZone().getID(), AMERICA_TIMEZONE);
			int days =Days.daysBetween(dtf.parseDateTime(rangeOne), dtf.parseDateTime(rangeTwo)).getDays();
			for(int i=0; i<days;i++){
				DateTime  time = dtf.parseDateTime(rangeOne).plusDays(i);
				listOfDays.add(time);
			}
			
			return listOfDays;
		} else if (dayRef.equalsIgnoreCase("week-before-seven-days")) {
			srcDateTime = srcDateTime.minusDays(14);
			rangeOne = getStartOfDay(srcDateTime, srcDateTime.getZone().getID(), AMERICA_TIMEZONE);
			srcDateTime = srcDateTime.plusDays(8);
			rangeTwo = getEndOfDay(srcDateTime, srcDateTime.getZone().getID(), AMERICA_TIMEZONE);
			int days =Days.daysBetween(dtf.parseDateTime(rangeOne), dtf.parseDateTime(rangeTwo)).getDays();
			for(int i=0; i<days;i++){
				DateTime  time = dtf.parseDateTime(rangeOne).plusDays(i);
				listOfDays.add(time);
			}
			return listOfDays;
		} else if (dayRef.equalsIgnoreCase("thirty-days")) {
			srcDateTime = srcDateTime.minusDays(30);
			rangeOne = getStartOfDay(srcDateTime, srcDateTime.getZone().getID(), AMERICA_TIMEZONE);
			srcDateTime = srcDateTime.plusDays(30);
			rangeTwo = getEndOfDay(srcDateTime, srcDateTime.getZone().getID(), AMERICA_TIMEZONE);
			int days =Days.daysBetween(dtf.parseDateTime(rangeOne), dtf.parseDateTime(rangeTwo)).getDays();
			for(int i=0; i<days;i++){
				DateTime  time = dtf.parseDateTime(rangeOne).plusDays(i);
				listOfDays.add(time);
			}
			return listOfDays;
		} else if (dayRef.equalsIgnoreCase("month-before-thirty-days")) {
			srcDateTime = srcDateTime.minusDays(60);
			rangeOne = getStartOfDay(srcDateTime, srcDateTime.getZone().getID(), AMERICA_TIMEZONE);
			srcDateTime = srcDateTime.plusDays(30);
			rangeTwo = getEndOfDay(srcDateTime, srcDateTime.getZone().getID(), AMERICA_TIMEZONE);
			int days =Days.daysBetween(dtf.parseDateTime(rangeOne), dtf.parseDateTime(rangeTwo)).getDays();
			for(int i=0; i<days;i++){
				DateTime  time = dtf.parseDateTime(rangeOne).plusDays(i);
				listOfDays.add(time);
			}
			return listOfDays;
		} else if (dayRef.equalsIgnoreCase("this-month")) {
			srcDateTime = srcDateTime.dayOfMonth().withMinimumValue();
			rangeOne = getStartOfDay(srcDateTime, srcDateTime.getZone().getID(), AMERICA_TIMEZONE);
			rangeTwo = getEndOfDay(new DateTime().plusDays(1), srcDateTime.getZone().getID(), AMERICA_TIMEZONE);
			int days =Days.daysBetween(dtf.parseDateTime(rangeOne), dtf.parseDateTime(rangeTwo)).getDays();
			for(int i=0; i<days;i++){
				DateTime  time = dtf.parseDateTime(rangeOne).plusDays(i);
				listOfDays.add(time);
			}
			return listOfDays;
		} else if (dayRef.equalsIgnoreCase("last-month")) {
			srcDateTime = srcDateTime.minusMonths(1).dayOfMonth().withMinimumValue();
			rangeOne = getStartOfDay(srcDateTime, srcDateTime.getZone().getID(), AMERICA_TIMEZONE);
			srcDateTime = srcDateTime.dayOfMonth().withMaximumValue();
			rangeTwo = getEndOfDay(srcDateTime.plusDays(1), srcDateTime.getZone().getID(), AMERICA_TIMEZONE);
			int days =Days.daysBetween(dtf.parseDateTime(rangeOne), dtf.parseDateTime(rangeTwo)).getDays();
			for(int i=0; i<days;i++){
				DateTime  time = dtf.parseDateTime(rangeOne).plusDays(i);
				listOfDays.add(time);
			}
			return listOfDays;
		} else if (dayRef.equalsIgnoreCase("last-last-month")) {
			srcDateTime = srcDateTime.minusMonths(2).dayOfMonth().withMinimumValue();
			rangeOne = getStartOfDay(srcDateTime, srcDateTime.getZone().getID(), AMERICA_TIMEZONE);
			srcDateTime = srcDateTime.dayOfMonth().withMaximumValue();
			rangeTwo = getEndOfDay(srcDateTime, srcDateTime.getZone().getID(), AMERICA_TIMEZONE);
			int days =Days.daysBetween(dtf.parseDateTime(rangeOne), dtf.parseDateTime(rangeTwo)).getDays();
			for(int i=0; i<days;i++){
				DateTime  time = dtf.parseDateTime(rangeOne).plusDays(i);
				listOfDays.add(time);
			}
			return listOfDays;
		} else if (dayRef.equalsIgnoreCase("this-year")) {
			srcDateTime = srcDateTime.dayOfYear().withMinimumValue();
			rangeOne = getStartOfDay(srcDateTime, srcDateTime.getZone().getID(), AMERICA_TIMEZONE);
			rangeTwo = getEndOfDay(new DateTime().plusDays(1), srcDateTime.getZone().getID(), AMERICA_TIMEZONE);
			int days =Days.daysBetween(dtf.parseDateTime(rangeOne), dtf.parseDateTime(rangeTwo)).getDays();
			for(int i=0; i<days;i++){
				DateTime  time = dtf.parseDateTime(rangeOne).plusDays(i);
				listOfDays.add(time);
			}
			return listOfDays;
		} else if (dayRef.equalsIgnoreCase("last-year")) {
			srcDateTime = srcDateTime.minusYears(1).dayOfYear().withMinimumValue();
			rangeOne = getStartOfDay(srcDateTime, srcDateTime.getZone().getID(), AMERICA_TIMEZONE);
			srcDateTime = srcDateTime.dayOfYear().withMaximumValue();
			rangeTwo = getEndOfDay(srcDateTime, srcDateTime.getZone().getID(), AMERICA_TIMEZONE);
			int days =Days.daysBetween(dtf.parseDateTime(rangeOne), dtf.parseDateTime(rangeTwo)).getDays();
			for(int i=0; i<days;i++){
				DateTime  time = dtf.parseDateTime(rangeOne).plusDays(i);
				listOfDays.add(time);
			}
			return listOfDays;
		} else if (dayRef.equalsIgnoreCase("custom")) {
			if(refFrom != null && refTo !=null){
				DateTime da = new DateTime();
				srcDateTime = dtf1.parseDateTime(refFrom);
				DateTime srcDateTime1 = dtf1.parseDateTime(refTo);
				if(da.getHourOfDay() <8){
					srcDateTime = srcDateTime.toDateTime(DateTimeZone.forID(AMERICA_TIMEZONE)).withHourOfDay(0).withMinuteOfHour(00).withSecondOfMinute(00).plusDays(1);
					srcDateTime1 = srcDateTime1.toDateTime(DateTimeZone.forID(AMERICA_TIMEZONE)).withHourOfDay(0).withMinuteOfHour(00).withSecondOfMinute(00).plusDays(1);
				}else {
					srcDateTime = srcDateTime.toDateTime(DateTimeZone.forID(AMERICA_TIMEZONE)).withHourOfDay(0).withMinuteOfHour(00).withSecondOfMinute(00).plusDays(1);
					srcDateTime1 = srcDateTime1.toDateTime(DateTimeZone.forID(AMERICA_TIMEZONE)).withHourOfDay(0).withMinuteOfHour(00).withSecondOfMinute(00).plusDays(1);
					
				}
				rangeOne = dtf.print(srcDateTime);
				rangeTwo = dtf.print(srcDateTime1);
				int days = Days.daysBetween(srcDateTime, srcDateTime1).getDays();
				for(int i=0; i<=days;i++){
					DateTime  time = dtf.parseDateTime(rangeOne).plusDays(i);
					listOfDays.add(time);
				}
				return listOfDays;
			}
		} else if (dayRef.equalsIgnoreCase("previous-custom")) {
			if(refFrom != null && refTo !=null){
				DateTime da = new DateTime();
				srcDateTime = dtf1.parseDateTime(refFrom);
				DateTime srcDateTime1 = dtf1.parseDateTime(refTo);
				if(da.getHourOfDay() <8){
					srcDateTime = srcDateTime.toDateTime(DateTimeZone.forID(AMERICA_TIMEZONE)).withHourOfDay(0).withMinuteOfHour(00).withSecondOfMinute(00).plusDays(1);
					srcDateTime1 = srcDateTime1.toDateTime(DateTimeZone.forID(AMERICA_TIMEZONE)).withHourOfDay(0).withMinuteOfHour(00).withSecondOfMinute(00).plusDays(1);
				}else {
					srcDateTime = srcDateTime.toDateTime(DateTimeZone.forID(AMERICA_TIMEZONE)).withHourOfDay(0).withMinuteOfHour(00).withSecondOfMinute(00).plusDays(1);
					srcDateTime1 = srcDateTime1.toDateTime(DateTimeZone.forID(AMERICA_TIMEZONE)).withHourOfDay(0).withMinuteOfHour(00).withSecondOfMinute(00).plusDays(1);
				}
				int days = Days.daysBetween(srcDateTime, srcDateTime1).getDays();
				rangeOne = dtf.print(srcDateTime.minusDays(days));
				rangeTwo = dtf.print(srcDateTime.minusDays(1));
				for(int i=0; i<=days;i++){
					DateTime  time = dtf.parseDateTime(rangeOne).plusDays(i);
					listOfDays.add(time);
				}
				return listOfDays;
			}
		}
		return null;
	}
	
	public static Map<Integer, String> getMonthsData(String refFrom, String refTo){
		DateTime srcDateTime = new DateTime().toDateTime(DateTimeZone.forID(AMERICA_TIMEZONE)).withHourOfDay(0).withMinuteOfHour(00).withSecondOfMinute(00);
		Map<Integer, String> listMonth = new LinkedHashMap<Integer, String>();
		if(refFrom != null && refTo !=null){
			srcDateTime = dtf1.parseDateTime(refFrom);
			DateTime srcDateTime1 = dtf1.parseDateTime(refTo);
			DateTime dt = new DateTime(srcDateTime.getYear(), srcDateTime.getMonthOfYear(), srcDateTime.getDayOfMonth(), 00, 00,00, DateTimeZone.forID(srcDateTime.getZone().getID()));
			DateTime dtLos = dt.withZone(DateTimeZone.forID(UTC_TIMEZONE));
			LocalDate date1 = new LocalDate(dtLos);
			dt = new DateTime(srcDateTime1.getYear(), srcDateTime1.getMonthOfYear(), srcDateTime1.getDayOfMonth(), 23, 59,59, DateTimeZone.forID(srcDateTime1.getZone().getID()));
			dtLos = dt.withZone(DateTimeZone.forID(UTC_TIMEZONE));
			LocalDate date2 = new LocalDate(dtLos); 
			//int i =0;
			while(date1.isBefore(date2)){
				listMonth.put(date1.getMonthOfYear() , date1.toString("MMM"));
				date1 = date1.plus(Period.months(1));
				//i++;
			}
		}
		return listMonth;
	}

	public static long getLongDate(String dateObject) {
		String longDate = dateObject.substring(0, 16).replace("-", "").replace(" ", "").replace(":", "");
		return Long.valueOf(longDate);
	}
	
	public static String getEtcToTimezoneTime(DateTime datetime, String timeZone){
		 DateTime dt = new DateTime(datetime.getYear(), datetime.getMonthOfYear(), datetime.getDayOfMonth(), datetime.getHourOfDay(), datetime.getMinuteOfHour(), DateTimeZone.forID("Etc/UTC"));
		 DateTime dtLos = dt.withZone(DateTimeZone.forID(timeZone));
		return dtf.print(dtLos);
	}
	
	public static String[] getDateRange(Request input, Context context){
		try {
			Object[] calenArray = CallXDateTimeConverterUtil.getArryOfCalObjects(input.getRef(),input.getRefFrom(),input.getRefTo());
			String[] dateRange = new String[2];
			dateRange[0] = String.valueOf(CallXDateTimeConverterUtil.getLongDate(calenArray[0].toString()));
			dateRange[1] = String.valueOf(CallXDateTimeConverterUtil.getLongDate(calenArray[1].toString()));
			return dateRange;
		}catch(Exception e) {
			context.getLogger().log("Error while converting the Dates : "+e.getMessage());
		}
		
		
		return null;
	}
	
	
	public static String convertSecondsToTimeFormat(int seconds) {
		int hours = seconds / 3600;
		int minutes = (seconds - hours * 3600) / 60;
		int sec = (seconds - hours * 3600) % 60;
		String time = null;
		if (seconds >= 3600) {
			time = (hours < 10 ? ("0" + hours) : hours) + ":" + (minutes < 10 ? ("0" + minutes) : minutes) + ":"
					+ (sec < 10 ? ("0" + sec) : sec);
		} else if (seconds < 3600) {
			time = (minutes < 10 ? ("0" + minutes) : minutes) + ":" + (sec < 10 ? ("0" + sec) : sec);
		}
		return time;
	}
	
	
	
	
}
