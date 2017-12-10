package com.achilles.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {
	public static String GetTimeStr(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.SIMPLIFIED_CHINESE);
		String strTime = sdf.format(new Date(time));
		return strTime;
	}
	
	public static String GetCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.SIMPLIFIED_CHINESE);
		String timenow = sdf.format(new Date());
		return timenow;
	}
	
	public static String GetCurrentTime(String timeFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(timeFormat,
				Locale.SIMPLIFIED_CHINESE);
		String timenow = sdf.format(new Date());
		return timenow;
	}
	
	public static String GetDayDesc(int day) {
		String name = "";
		switch(day) {
		case 0:
			name = "星期二";
			break;
		case 1:
			name = "星期三";
			break;
		case 2:
			name = "星期四";
			break;
		case 3:
			name = "星期五";
			break;
		case 4:
			name = "星期六";
			break;
		case 5:
			name = "星期日";
			break;
		default:
			name = "日期错误";
				break;
		}
		return name;
	}
}
