package org.patent.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private static final String formate="yyyy-MM-dd HH:mm:ss";

	public static String changeDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formate);
		return simpleDateFormat.format(date);
	}

	public static Date stringToDate(String time) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formate);
		try {
			return simpleDateFormat.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
			
	}

}
