package com.cyg.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cyg.framework.exception.UtilException;
 

/**
* @Description: 日期时间工具类。  
* @author 王道兵 
*/
public class DateUtil {
	
	private static String date_format=SystemPropertiesUtil.getRequiredProperty("date_string.format");
	
	private static String datetime_format=SystemPropertiesUtil.getRequiredProperty("datetime_string.format");
	
	public static void setDateFormatString(String format){
		date_format=format;
	}
	public static void setDateTimeFormatString(String format){
		datetime_format=format;
	}
	/**
	 * 将一个毫秒的时间转换为中文时间字符。如传入2000会返回2秒。
	 * 
	 * @param time
	 *            毫秒
	 * @return
	 */
	public static String timeIntervalToString(Long time) {
		if (time == null)
			return "";
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		long day = time / dd;
		long hour = (time - day * dd) / hh;
		long minute = (time - day * dd - hour * hh) / mi;
		long second = (time - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = time - day * dd - hour * hh - minute * mi - second
				* ss;

		StringBuilder str = new StringBuilder();
		if (day > 0) {
			str.append(day).append("天,");
		}
		if (hour > 0) {
			str.append(hour).append("小时,");
		}
		if (minute > 0) {
			str.append(minute).append("分钟,");
		}
		if (second > 0) {
			str.append(second).append("秒,");
		}
		if (milliSecond > 0) {
			str.append(milliSecond).append("毫秒,");
		}
		if (str.length() > 0) {
			str = str.deleteCharAt(str.length() - 1);
		}
		return str.toString();
	}
	
	/**
	 * 将java date按照平台配置文件配置的日期字符格式化为字符串
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date){
		return dateToString(date,date_format);
	}
	/**
	 * 将java date按照平台配置文件配置的日期字符格式化为字符串
	 * @param date
	 * @return
	 */
	public static String datetimeToString(Date date){
		return dateToString(date,datetime_format);
	}
	
	/**
	 * 将java date按照传入的日期字符格式化为字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToString(Date date,String format){
		String target=null;
		if(date == null || "".equals(date))
		    return null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		target=simpleDateFormat.format(date);
		return target;
	}
	
	/**
	 * 将日期字符串转换为java.util.Date类型.转换格式为平台配置文件中date_string.format的值
	 * @param dateString 日期字符串
	 * @return
	 */
	public static Date stringToDate(String dateString){
		return stringToDate(dateString,date_format);
	}
	
	
	/**
	 * 将日期字符串转换为java.util.Date类型.转换格式为平台配置文件中datetime_string.format的值
	 * @param dateString 日期字符串
	 * @return
	 */
	public static Date stringToDatetime(String dateString){
		return stringToDate(dateString,datetime_format);
	}
	
	/**
	 * 用传入的日期字符串格式格式化字符。
	 * @param dataString 日期字符串
	 * @param format 转换格式
	 * @return
	 */
	public static Date stringToDate(String dataString,String format){
		Date target=null;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			target=simpleDateFormat.parse(dataString);
		} catch (ParseException e) {
			throw new UtilException("将["+dataString+"]转换为["+format+"]格式的日期时发生错误。");
		}
		return target;
	}
	

	public static void main(String args[]) {
		System.out.println(DateUtil.timeIntervalToString(new Long(257899800)));
	}

	/** 计算年龄 */ 
	public  static String getAge(Date birthDay) throws Exception { 
        Calendar cal = Calendar.getInstance(); 

        if (cal.before(birthDay)) { 
            throw new IllegalArgumentException( 
                "生日晚于系统时间!"); 
        } 

        int yearNow = cal.get(Calendar.YEAR); 
        int monthNow = cal.get(Calendar.MONTH)+1; 
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); 
        
        cal.setTime(birthDay); 
        int yearBirth = cal.get(Calendar.YEAR); 
        int monthBirth = cal.get(Calendar.MONTH)+1; 
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH); 

        int age = yearNow - yearBirth; 

        if (monthNow <= monthBirth) { 
            if (monthNow == monthBirth) { 
                //monthNow==monthBirth 
                if (dayOfMonthNow < dayOfMonthBirth) { 
                    age--; 
                } 
            } else { 
                //monthNow>monthBirth 
                age--; 
            } 
        } 

        return age +""; 
    }
	/**
	 * 依据给定的开始时间、结束时间按求出相应的日期集合
	 * @param beginDate 开始时间
	 * @param endDate  结束时间
	 * @return 日期集合
	 */
	public static List<String> getBetweenDate(String beginDate,String endDate){
		
		  List<String> dateList=new ArrayList<String>();
		try{
		
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date begin =sdf.parse(beginDate);
			Date end=sdf.parse(endDate);
			if(begin.after(end)){  //开始时间不能大于结束时间
				throw new UtilException("开始日期("+beginDate+")不能大于结束日期("+"endDate"+")");
			}
			Calendar  beginCalendar=GregorianCalendar.getInstance(); 
			beginCalendar.setTime(begin);
			Calendar  endCalendar=GregorianCalendar.getInstance(); 
			endCalendar.setTime(end);
			int beginYear=beginCalendar.get(Calendar.YEAR);   // 开始年份
			int beginMonth=beginCalendar.get(Calendar.MONTH)+1;  //开始月份
			int beginDay=beginCalendar.get(Calendar.DAY_OF_MONTH);  //开始天
			int endYear=endCalendar.get(Calendar.YEAR);
			int endMonth=endCalendar.get(Calendar.MONTH)+1;   //结束月份
			int endDay=endCalendar.get(Calendar.DAY_OF_MONTH);   //结束天
				for(int i=beginYear;i<=endYear;i++){
					int tempEndMonth=12;
					int tempBeginMonth=1;
					if(i==endYear)
						tempEndMonth=endMonth;
					if(i==beginYear)
						tempBeginMonth=beginMonth;
				    for(int j=tempBeginMonth;j<=tempEndMonth;j++){
                          int tempDays=getDays(i, j);
                          int tempBeginDays=1;
                          if(i==endYear&&j==tempEndMonth)
                        	  tempDays=endDay;
                          if(i==beginYear&&j==tempBeginMonth)
                        	  tempBeginDays=beginDay;
                          for(int k=tempBeginDays;k<=tempDays;k++){
                        	  dateList.add(i+"-"+(j<10?"0"+j:j)+"-"+(k<10?"0"+k:k));
                          }
				    }
				}
			
		}catch(Exception e){
			  dateList.clear();
			  throw new UtilException("获取日期集合发生错误",e);
		}
		
		
		return dateList;
	}
	
	//根据年度，月份求出当月的天数
	public static int getDays(int year,int month){
		if(month==1 ||month==3||month==5||month==7||month==8||month==10||month==12)
			return 31;
		else if(month==2){
			if(isRunNian(year))
				return 29;
			else 
				return 28;
		}
		else 
			return 30;
	}
	
	/**
	 * 根据年度，月份求出当月的天数
	 *参数 格式"2015-1",参数错误返回-1
	 * @param date
	 * @return
	 */
	public static int getDays_(String date){
			if(date.indexOf("-") == -1){return -1;}
			String[] split = date.split("-");
			try {
				final int year =Integer.parseInt(split[0]);
				final int month =Integer.parseInt(split[1]);
				if(month==1 ||month==3||month==5||month==7||month==8||month==10||month==12)
					return 31;
				else if(month==2){
					if(isRunNian(year))
						return 29;
					else 
						return 28;
				}
				else 
					return 30;
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return -1;
			}
		}
	
	//判断是否闰年
	public static boolean isRunNian(int year){
		if((year%4==0&&year%100!=0)||year%400==0)
			return true;
		return false;
	}
	
	//判断是否是周末
	public  static boolean isWeekend(String date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date time = null;
		try {
			time = df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 获取当前年份,第一月和本月的日期,格式:yyyy-mm
	* @Title: getDateBeginAndEnd 
	* @Description: TODO
	* @param @param obj
	* @param @return
	* @return String
	* @throws
	 */
	public static Map<String,String> getDateBeginAndEnd(){
		Map<String,String> map = new HashMap<String,String>();
		String beginDate = "";
		String endDate = "";
		
		Date date = new Date();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy");
		beginDate = dateFormat1.format(date)+"-01";
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM");
		endDate = dateFormat2.format(date);
		
		map.put("beginDate", beginDate);
		map.put("endDate", endDate);
		return map;
	}
	
	/**
	 * 获取年初时间
	* @Title: getBeginDate 
	* @Description: TODO
	* @param @param yearStatus -1表示去年，1表示今年
	* @param @return
	* @return String
	* @throws
	 */
	public static String getBeginDate(int yearStatus){
		Calendar cal = Calendar.getInstance();
		if(yearStatus == -1){//-1表示去年，1表示今年
			cal.add(Calendar.YEAR, -1);
		}
		String dataStr=DateUtil.dateToString(cal.getTime(), "yyyy-01-01 00:00:00");
		return dataStr;
	}
	
	/**
	 * 获取当前月最后一天
	* @Title: getEndDate 
	* @Description: TODO
	* @param @param status -1表示去年，1表示今年
	* @param @return
	* @return String
	* @throws
	 */
	public static String getEndDate(int status){
		Calendar cal = Calendar.getInstance();
		if(status == -1){//-1表示去年，1表示今年
			cal.add(Calendar.YEAR, -1);
		}
		cal.add(Calendar.DAY_OF_YEAR, -1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

		String dataStr=DateUtil.dateToString(cal.getTime(), "yyyy-MM-dd 23:59:59");
		return dataStr;
	}
	
	/**
	 * 获取年份
	* @Title: getYear 
	* @Description: TODO
	* @param @param status -1表示去年
	* @param @return
	* @return String
	* @throws
	 */
	public static String getYear(int status){
		Calendar cal = Calendar.getInstance();
		if(status == -1){//-1表示去年，1表示今年
			cal.add(Calendar.YEAR, -1);
		}

		String dataStr=DateUtil.dateToString(cal.getTime(), "yyyy");
		return dataStr;
	}
	/**获取当前月份值*/
	public static int getMonth(){
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH)+1;
	}
	/**获取指定日期的月份值	*/
	public static int getMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH)+1;
	}
	
	/**获取当前时间的上个月*/
	public static String getUpMonthDate(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH,-1);
		String dataStr=DateUtil.dateToString(cal.getTime(), "yyyy-MM");
		return dataStr;
	}
	
	/**
	 * 获取当前月推N个月
	* @Title: getMonthByFlag 
	* @Description: TODO  注意：此方法获取的月份不能超过当年的月份
	* @param @param status 往前推N月 如：0时表示当前月； 1表示下个月；-1表示
	* @param @return
	* @return int
	* @throws
	 */
	public static int getMonthByStatus(int status){
		int resultMonth;
		Calendar cal = Calendar.getInstance();
		if(status==0){
			resultMonth = getMonth();
		}else{
			resultMonth = cal.get(Calendar.MONTH)+1+status;
		}
		return resultMonth;
	}
	
	/**
	 * 将yyyy-mm格式转换成yyyy-mm-dd hh:mm:ss sql查询可用格式
	* @Title: getSqlDate 
	* @Description: TODO
	* @param @param date 传进来的yyyy-mm格式日期字符串
	* @param @param status 1表示取月初（-01） 2表示取月末（-28 or -29 or -30 or -31）
	* @param @return
	* @return String
	* @throws
	 */
	public static String getSqlDate(String date,int status){
		String sqlDate = "";
		if(status ==  1){//月初
			sqlDate = date + "-01 00:00:00";//月初
		}else{
			String dates[] = date.split("-");
			int lastDay = getDays(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]));
			sqlDate = date + "-"+lastDay+" 23:59:59";//月末
		}
		return sqlDate;
	}
	
	/**
	 * 计算两个日期的天数差，支持跨年
	 * @param exitDateFrom
	 * @param exitDateTo
	 * @return
	 */
	public static int getIntervalDaysOfExitDate2(Date exitDateFrom, Date exitDateTo){
        Calendar aCalendar = Calendar.getInstance();
        Calendar bCalendar = Calendar.getInstance();
        aCalendar.setTime(exitDateFrom);
        bCalendar.setTime(exitDateTo);
        int days = 0;
        while(aCalendar.before(bCalendar)){
            days++;
            aCalendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        return days;
    }
	
	/**
	 * 日期加减指定天数的日期
	 * @param date
	 * @param val
	 * @return
	 */
	public static Date getAddAndSubDayDate(Date date, int val){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, val);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
    }
	
	/**
	 * 日期加减指定月份的日期
	 * @param date
	 * @param val
	 * @return
	 */
	public static Date getAddAndSubMonthDate(Date date, int val){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, val);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
    }
	
	/**
	 * 指定日期的第一天
	 * @param date
	 * @param val
	 * @return
	 */
	public static Date getFirstDayByMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
    }
}
