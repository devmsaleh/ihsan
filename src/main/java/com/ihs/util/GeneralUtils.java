package com.ihs.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

public class GeneralUtils {

	// date formats should be ddmmyy

	public static final String DATE_FORMATE_FOR_DISPLAY = "yyyy-MM-dd";
	public static final String DATE_TIME_FORMATE_FOR_DISPLAY_ARABIC = "hh:mm yyyy-MM-dd a";
	public static final String DATE_TIME_FORMATE_FOR_DISPLAY_ENGLISH = "dd-MM-yyyy hh:mm a";
	public static final String DATE_FORMATE_INPUT_BY_USER = "yyyyMMdd";

	public static Date parseDate(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATE_INPUT_BY_USER);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String formateDate(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATE_FOR_DISPLAY);
		try {
			return sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String formateDateTime(Date date) {
		return formateDateTime(date, "ar");
	}

	public static String formateDateTime(Date date, String lang) {
		if (date == null)
			return "";
		String format = DATE_TIME_FORMATE_FOR_DISPLAY_ARABIC;
		if (StringUtils.isBlank(lang)) {
			lang = "ar";
		}
		if (lang.equalsIgnoreCase("en")) {
			format = DATE_TIME_FORMATE_FOR_DISPLAY_ENGLISH;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format, new Locale(lang));
		try {
			return sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean isMobileString(String searchText) {
		return searchText.startsWith("5") || searchText.startsWith("05");
	}

	public static boolean isEmptyNumber(Object obj) {
		if (obj == null)
			return true;
		if (obj instanceof BigDecimal) {
			BigDecimal number = (BigDecimal) obj;
			return number.compareTo(BigDecimal.ZERO) <= 0;
		}
		if (obj instanceof BigInteger) {
			BigInteger number = (BigInteger) obj;
			return number.compareTo(BigInteger.ZERO) <= 0;
		}
		if (obj instanceof String) {
			try {
				return Long.parseLong(obj.toString()) <= 0;
			} catch (Exception e) {
				return true;
			}
		}
		return true;

	}

	public static void main(String[] args) {
		System.out.println(isEmptyNumber("5"));
	}

}
