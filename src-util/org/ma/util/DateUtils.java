package org.ma.util;


import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理工具
 *
 */
public class DateUtils {
	
	//传输格式
	public final static String TRANS_DATE_FORMAT = "yyyyMMddHHmmss";
	
	//长日期格�?
	public final static String LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	
	//短日期格�?
	public final static String SHORT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	//日期格式
	public final static String DATE_FORMAT = "yyyy-MM-dd";
	
	//时间格式
	public final static String TIME_FORMAT_HHmm = "HH:mm";
	
	//�?��的最初时�?
	public final static String FIRST_TIME_FORMAT = "yyyy-MM-dd 00:00:00.000";
	
	//�?��的最后时�?
	public final static String LAST_TIME_FORMAT = "yyyy-MM-dd 23:59:59.999";
	
	//日期格式
	public final static String[] DATE_PATTERNS = 
					new String[]{"yyyy-MM-dd HH:mm:ss.SSS","yyyy/MM/dd HH:mm:ss.SSS","MM/dd/yyyy HH:mm:ss.SSS",
								"yyyy-MM-dd HH:mm:ss","yyyy/MM/dd HH:mm:ss","MM/dd/yyyy HH:mm:ss",
								"yyyy-MM-dd HH:mm","yyyy/MM/dd HH:mm","MM/dd/yyyy HH:mm",
								"yyyy-MM-dd HH","yyyy/MM/dd HH","MM/dd/yyyy HH",
								"yyyy-MM-dd","yyyy/MM/dd","MM/dd/yyyy"};

	
	
	/**
	 * 将一个字符串按默认格式转换为日期对象
	 * @param str		日期字符�?
	 * @return
	 */
	public static Date parseDate(long d){
		return new Date(d);
	}	
	/**
	 * 将一个字符串按默认格式转换为日期对象
	 * @param str		日期字符�?
	 * @return
	 */ 
	public static Date parseDate(String str){
		return parseDate(str,DATE_PATTERNS);
	}	
	/**
	 * 将一个字符串按指定格式转换为日期对象
	 * @param src		要转换的字符�?
	 * @param format	日期格式
	 * @return			
	 */
	public static Date parseDate(String src,String format){
		Date date = null;
		if(src==null || src.trim().equals("") || src.trim().equals("null") 
				|| src.trim().equals("NULL")){
			return null;
		}
		try{
			date=new SimpleDateFormat(format).parse(src.trim());
		}catch(Exception e){}
		return date;
	}
	/**
	 * 将一个字符串按指定格式转换为日期对象
	 * @param str			要转换的字符�?
	 * @param parsePatterns	可能出现的所有日期格�?
	 * @return
	 * @throws ParseException
	 */
    public static Date parseDate(String str, String[] parsePatterns){
        if (str == null  || str.trim().equals("") || parsePatterns == null) {
            return null;
        }
        
        SimpleDateFormat parser = null;
        ParsePosition pos = new ParsePosition(0);
        for (int i = 0; i < parsePatterns.length; i++) {
            if (i == 0) {
                parser = new SimpleDateFormat(parsePatterns[0]);
            } else {
                parser.applyPattern(parsePatterns[i]);
            }
            pos.setIndex(0);
            Date date = parser.parse(str, pos);
            if (date != null && pos.getIndex() == str.length()) {
                return date;
            }
        }
        return null;
    }	
    /**
     * 将一个格式的日期字符串转换为另一种格式的日期字符�?
     * @param str			日期字符�?
     * @param srcFormat		源字符串格式
     * @return
     */
    public static String convertDateFormat(String str,String dstFormat){
    	return convertDateFormat(str,DATE_PATTERNS,dstFormat);
    }
    /**
     * 将一个格式的日期字符串转换为另一种格式的日期字符�?
     * @param str			日期字符�?
     * @param srcFormat		源字符串格式
     * @param dstFormat		目标字符串格�?
     * @return
     */
    public static String convertDateFormat(String str,String srcFormat, String dstFormat){
    	return convertDateFormat(str,new String[]{srcFormat},dstFormat);
    }  
    /**
     * 将一个格式的日期字符串转换为另一种格式的日期字符�?
     * @param str			日期字符�?
     * @param srcFormats	源字符串格式
     * @param dstFormat		目标字符串格�?
     * @return
     */
    public static String convertDateFormat(String str,String[] srcFormats, String dstFormat){
    	if(str==null || str.trim().equals(""))
    		return str;
    	if(srcFormats==null || srcFormats.length==0 || dstFormat==null)
    		throw new IllegalArgumentException("日期格式不能为空");

    	Date date = parseDate(str,srcFormats);
    	if(date==null)
    		return null;
    	else
    		return formatDate(date,dstFormat);
    } 
	/**
	 * 将一个日期对象按指定格式转换为字符串
	 * @param date			日期毫秒�?
	 * @param format		日期格式
	 * @return
	 */
	public static String formatDate(long d,String format){
		return new SimpleDateFormat(format).format(new Date(d));
	}    
	/**
	 * 将一个日期对象按指定格式转换为字符串
	 * @param date			日期对象
	 * @param format		日期格式
	 * @return
	 */
	public static String formatDate(Date d,String format){
		if(d==null)
			return null;
		else
			return new SimpleDateFormat(format).format(d);
	}
	
	/**
	 * 在日期指定位置上加上某�?后获得一个新的时�?
	 * @param date		日期对象
	 * @param field		位置
	 * @param time		时间
	 * @return
	 */
	public static Date add(Date date, int field, int time){
		if(date==null || time==0)
			return date;
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, time);
		return calendar.getTime();
	}	
	/**
	 * 在原日期上加上指定的年数后返回一个新的日期对�?
	 * @param date		日期对象
	 * @param year		年数
	 * @return
	 */
	public static Date addYear(Date date, int year){
		return add(date,Calendar.YEAR,year);
	}	
	/**
	 * 在原日期上加上指定的月数后返回一个新的日期对�?
	 * @param date		日期对象
	 * @param month		月数
	 * @return
	 */
	public static Date addMonth(Date date, int month){
		return add(date,Calendar.MONTH,month);
	}	
	/**
	 * 在原日期上加上指定的天数后返回一个新的日期对�?
	 * @param date		日期对象
	 * @param day		天数
	 * @return
	 */
	public static Date addDay(Date date, int day){
		return add(date,Calendar.DATE,day);
	}		
	/**
	 * 在原日期上加上指定的小时数后返回�?��新的日期对象
	 * @param date		日期对象
	 * @param hour		小时�?
	 * @return
	 */
	public static Date addHour(Date date, int hour){
		return add(date,Calendar.HOUR,hour);
	}
	/**
	 * 在原日期上加上指定的分钟数后返回�?��新的日期对象
	 * @param date		日期对象
	 * @param minute	分钟�?
	 * @return
	 */
	public static Date addMinute(Date date, int minute){
		return add(date,Calendar.MINUTE,minute);
	}	
	/**
	 * 在原日期上加上指定的秒数后返回一个新的日期对�?
	 * @param date		日期对象
	 * @param second	秒数
	 * @return
	 */
	public static Date addSecond(Date date, int second){
		return add(date,Calendar.SECOND,second);
	}
	/**
	 * 在日期指定位置上赋某设置�?
	 * @param date		日期对象
	 * @param field		位置
	 * @param time		时间
	 * @return
	 */
	public static Date set(Date date, int field, int time){
		if(date==null || time==0)
			return date;
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(field, time);
		return calendar.getTime();
	}	
	/**
	 * 在原日期上设置年数为�?��定�?
	 * @param date		日期对象
	 * @param year		年数
	 * @return
	 */
	public static Date setYear(Date date, int year){
		return set(date,Calendar.YEAR,year);
	}	
	/**
	 * 在原日期上设置月数为�?��定�?
	 * @param date		日期对象
	 * @param month		月数
	 * @return
	 */
	public static Date setMonth(Date date, int month){
		if(month==1)
			month = 12;
		else
			month = month - 1;
		return set(date,Calendar.MONTH,month);
	}	
	/**
	 * 在原日期上设置天数为�?��定�?
	 * @param date		日期对象
	 * @param day		天数
	 * @return
	 */
	public static Date setDay(Date date, int day){
		return set(date,Calendar.DATE,day);
	}		
	/**
	 * 在原日期上设置小时数为一指定�?
	 * @param date		日期对象
	 * @param hour		小时�?
	 * @return
	 */
	public static Date setHour(Date date, int hour){
		return set(date,Calendar.HOUR,hour);
	}
	/**
	 * 在原日期上设置分钟数为一指定�?
	 * @param date		日期对象
	 * @param minute	分钟�?
	 * @return
	 */
	public static Date setMinute(Date date, int minute){
		return set(date,Calendar.MINUTE,minute);
	}	
	/**
	 * 在原日期上设置秒数为�?��定�?
	 * @param date		日期对象
	 * @param second	秒数
	 * @return
	 */
	public static Date setSecond(Date date, int second){
		return set(date,Calendar.SECOND,second);
	}	
	/**
	 * 得到当前日期当天的最早时�?
	 * @param date		日期对象
	 * @return
	 */
	public static Date getFirstTime(Date date){
		if(date==null)
			return null;
		return parseDate(formatDate(date,FIRST_TIME_FORMAT),LONG_DATE_FORMAT);
	}
	/**
	 * 得到当前日期本周的最早时�?
	 * @param date		日期对象
	 * @return
	 */
	public static Date getFirstTimeOfWeek(Date date){
		if(date==null)
			return null;
		int day = getDayAtWeek(date);
		return getFirstTime(addDay(date,-day+1));
	}		
	/**
	 * 得到当前日期本月的最早时�?
	 * @param date		日期对象
	 * @return
	 */
	public static Date getFirstTimeOfMonth(Date date){
		if(date==null)
			return null;
		date = setDay(date,1);
		return parseDate(formatDate(date,FIRST_TIME_FORMAT),LONG_DATE_FORMAT);
	}
	/**
	 * 得到当前日期本年的最早时�?
	 * @param date		日期对象
	 * @return
	 */
	public static Date getFirstTimeOfYear(Date date){
		if(date==null)
			return null;
		date = setDay(date,1);
		date = setMonth(date,1);
		return parseDate(formatDate(date,FIRST_TIME_FORMAT),LONG_DATE_FORMAT);
	}	
	/**
	 * 得到当前日期当天的最晚时�?
	 * @param date		日期对象
	 * @return
	 */
	public static Date getLastTime(Date date){
		if(date==null)
			return null;
		return parseDate(formatDate(date,LAST_TIME_FORMAT),LONG_DATE_FORMAT);
	}
	/**
	 * 得到当前日期本周的最晚时�?
	 * @param date		日期对象
	 * @return
	 */
	public static Date getLastTimeOfWeek(Date date){
		if(date==null)
			return null;
		int day = getDayAtWeek(date);
		return getLastTime(addDay(date,7-day));
	}	
	/**
	 * 得到当前日期本月的最早时�?
	 * @param date		日期对象
	 * @return
	 */
	public static Date getLastTimeOfMonth(Date date){
		if(date==null)
			return null;
		date = addMonth(date,1);
		date = setDay(date,1);
		date = addDay(date,-1);
		return parseDate(formatDate(date,LAST_TIME_FORMAT),LONG_DATE_FORMAT);
	}
	/**
	 * 得到当前日期本年的最晚时�?
	 * @param date		日期对象
	 * @return
	 */
	public static Date getLastTimeOfYear(Date date){
		if(date==null)
			return null;
		date = setDay(date,1);
		date = setMonth(date,1);
		date = addYear(date,1);
		date = addDay(date,-1);
		return parseDate(formatDate(date,LAST_TIME_FORMAT),LONG_DATE_FORMAT);
	}	
	/**
	 * 得到日期在这�?��的第几天
	 * @param date		日期对象
	 * @return
	 */
	public static int getDayAtYear(Date date){
		if(date==null)
			throw new IllegalArgumentException("日期不能为空");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_YEAR);
	}	
	/**
	 * 得到日期在这�?��的第几天
	 * @param date		日期对象
	 * @return
	 */
	public static int getDayAtMonth(Date date){
		if(date==null)
			throw new IllegalArgumentException("日期不能为空");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	/**
	 * 得到日期在这�?��的第几天
	 * @param date		日期对象
	 * @return
	 */
	public static int getDayAtWeek(Date date){
		if(date==null)
			throw new IllegalArgumentException("日期不能为空");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}	
	/**
	 * 得到日期在这�?��的第几周
	 * @param date		日期对象
	 * @return
	 */
	public static int getWeekAtMonth(Date date){
		if(date==null)
			throw new IllegalArgumentException("日期不能为空");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
	}
	/**
	 * 得到日期在这�?��的第几周
	 * @param date		日期对象
	 * @return
	 */
	public static int getWeekAtYear(Date date){
		if(date==null)
			throw new IllegalArgumentException("日期不能为空");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}
	/**
	 * 得到日期在这�?��的第几月
	 * @param date		日期对象
	 * @return
	 */
	public static int getMonthAtYear(Date date){
		if(date==null)
			throw new IllegalArgumentException("日期不能为空");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);
		if(month==12)
			return 1;
		else
			return month+1;
	}	
	/**
	 * 得到日期�?��的这�?��有多少天
	 * @param date		日期对象
	 * @return
	 */
	public static int getDayOfYear(Date date){
		if(date==null)
			throw new IllegalArgumentException("日期不能为空");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}	
	/**
	 * 得到日期�?��的这�?��有多少天
	 * @param date		日期对象
	 * @return
	 */
	public static int getDayOfMonth(Date date){
		Date d1 = setDay(date,1);
		Date d2 = addMonth(d1,1);
		return getDayOfBetweenDate(d2,d1);
	}
	/**
	 * 得到两日期间间相差的天数
	 * @param date1		�?��日期
	 * @param date2		结束日期
	 * @return
	 */
	public static int getDayOfBetweenDate(Date date1,Date date2){
		if(date1==null || date2==null)
			throw new IllegalArgumentException("日期不能为空");
		long l = date1.getTime() - date2.getTime();
		long days = l/1000/60/60/24;
		
		return new Long(days).intValue();
	}
	/**
	 * 得到两日期间间相差的小时�?
	 * @param date1		�?��日期
	 * @param date2		结束日期
	 * @return
	 */
	public static int getHourOfBetweenDate(Date date1,Date date2){
		if(date1==null || date2==null)
			throw new IllegalArgumentException("日期不能为空");
		long l = date1.getTime() - date2.getTime();
		long days = l/1000/60/60;
		
		return new Long(days).intValue();
	}
	
	/**
	 * 得到当前时间指定天数前后的开始时�?
	 * @param day	天数，正数则为之后，负数为之�?
	 * @return
	 */
	public static Date getBeginTime(int day){
		Calendar time = Calendar.getInstance();
		time.add(Calendar.DATE, day);
		time.set(Calendar.HOUR_OF_DAY, 0);
		time.set(Calendar.MINUTE, 0);
		time.set(Calendar.SECOND, 0);
		time.set(Calendar.MILLISECOND, 0);
		
		return time.getTime();
	}
	
	/**
	 * 得到当前时间指定天数前后的结束时�?
	 * @param day	天数，正数则为之后，负数为之�?
	 * @return
	 */
	public static Date getEndTime(int day){
		Calendar time = Calendar.getInstance();
		time.add(Calendar.DATE, day);
		time.set(Calendar.HOUR_OF_DAY, 23);
		time.set(Calendar.MINUTE, 59);
		time.set(Calendar.SECOND, 59);
		time.set(Calendar.MILLISECOND, 999);
		
		return time.getTime();
	}
	
	/**
	 * 得到当前时间指定周数前后的开始时�?
	 * @param week	周数，正数则为之后，负数为之�?
	 * @return
	 */
	public static Date getBeginTimeOfWeek(int week){
		Calendar time = Calendar.getInstance();
		time.add(Calendar.WEEK_OF_YEAR, week);
		time.set(Calendar.DAY_OF_WEEK, 2);
		time.set(Calendar.HOUR_OF_DAY, 0);
		time.set(Calendar.MINUTE, 0);
		time.set(Calendar.SECOND, 0);
		time.set(Calendar.MILLISECOND, 0);
		
		return time.getTime();
	}
	
	public static Date getBeginTimeOfYear(){
		Calendar c = Calendar.getInstance();
	//	c.set(Calendar.YEAR, );
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		
		
		return c.getTime();
	}
	
	
	/**
	 * 得到当前时间指定周数前后的开始时�?
	 * @param week	周数，正数则为之后，负数为之�?
	 * @return
	 */
	public static Date getEndTimeOfWeek(int week){
		Calendar time = Calendar.getInstance();
		time.add(Calendar.WEEK_OF_YEAR, week+1);
		time.set(Calendar.DAY_OF_WEEK, 2);
		time.set(Calendar.HOUR_OF_DAY, 23);
		time.set(Calendar.MINUTE, 59);
		time.set(Calendar.SECOND, 59);
		time.set(Calendar.MILLISECOND, 999);
		time.add(Calendar.DATE, -1);
		return time.getTime();
	}
	
	/**
	 * 得到当前时间指定月数前后的开始时�?
	 * @param month	月数，正数则为之后，负数为之�?
	 * @return
	 */
	public static Date getBeginTimeOfMonth(int month){
		Calendar time = Calendar.getInstance();
		time.add(Calendar.MONTH, month);
		time.set(Calendar.DAY_OF_MONTH, 1);
		time.set(Calendar.HOUR_OF_DAY, 0);
		time.set(Calendar.MINUTE, 0);
		time.set(Calendar.SECOND, 0);
		time.set(Calendar.MILLISECOND, 0);
		
		return time.getTime();
	}
	
	/**
	 * 得到当前时间指定月数前后的结束时�?
	 * @param month	月数，正数则为之后，负数为之�?
	 * @return
	 */
	public static Date getEndTimeOfMonth(int month){
		Calendar time = Calendar.getInstance();
		time.add(Calendar.MONTH, month+1);
		time.set(Calendar.DAY_OF_MONTH, 1);
		time.set(Calendar.HOUR_OF_DAY, 23);
		time.set(Calendar.MINUTE, 59);
		time.set(Calendar.SECOND, 59);
		time.set(Calendar.MILLISECOND, 999);
		time.add(Calendar.DATE, -1);
		return time.getTime();
	}
	
	public static void main(String args[]){
		Date date = new Date();
		System.out.println("getFirstTime="+formatDate(getFirstTime(date),LONG_DATE_FORMAT));
		System.out.println("getLastTime="+formatDate(getLastTime(date),LONG_DATE_FORMAT));
		
		System.out.println("getFirstTimeOfWeek="+formatDate(getFirstTimeOfWeek(date),LONG_DATE_FORMAT));
		System.out.println("getLastTimeOfWeek="+formatDate(getLastTimeOfWeek(date),LONG_DATE_FORMAT));
		
		System.out.println("getFirstTimeOfMonth="+formatDate(getFirstTimeOfMonth(date),LONG_DATE_FORMAT));
		System.out.println("getLastTimeOfMonth="+formatDate(getLastTimeOfMonth(date),LONG_DATE_FORMAT));	
		
		System.out.println("getFirstTimeOfYear="+formatDate(getFirstTimeOfYear(date),LONG_DATE_FORMAT));
		System.out.println("getLastTimeOfYear="+formatDate(getLastTimeOfYear(date),LONG_DATE_FORMAT));		
		
		System.out.println("getDayAtYear="+getDayAtYear(date));
		System.out.println("getDayAtMonth="+getDayAtMonth(date));		
		System.out.println("getDayAtWeek="+getDayAtWeek(date));		
		System.out.println("getWeekAtMonth="+getWeekAtMonth(date));
		System.out.println("getWeekAtYear="+getWeekAtYear(date));
		System.out.println("getMonthAtYear="+getMonthAtYear(date));
		
		System.out.println("getDayOfYear="+getDayOfYear(date));
		System.out.println("getDayOfMonth="+getDayOfMonth(date));
		
		System.out.println("getDayOfBetweenDate="+getDayOfBetweenDate(getLastTimeOfMonth(date),date));
		System.out.println("getHourOfBetweenDate="+getHourOfBetweenDate(getLastTimeOfMonth(date),date));
		
		System.out.println("--------------------------");
		
		date = new Date();
		
	
	}
}
