package com.lw.demo.android.samples.customviews;


import com.contrarywind.adapter.WheelAdapter;
import com.contrarywind.interfaces.IPickerViewData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Numeric Wheel adapter.
 */
public class NumericWheelAdapterExtend implements WheelAdapter {

	private int minValue;
	private int maxValue;
	private int year;
	private int month;
	private int startDay;
	private Date startDate;
	private Date endDate;
	Calendar mCalenadr;
	Calendar start;
	Calendar end;

	/**
	 * Constructor
	 * @param minValue the wheel min value
	 * @param maxValue the wheel max value
	 */
	public NumericWheelAdapterExtend(int year, int month, int minValue, int maxValue) {
		mCalenadr = Calendar.getInstance();
		mCalenadr.set(Calendar.YEAR,year);
		mCalenadr.set(Calendar.MONTH,month);
		this.year = year;
		this.month = month;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}
	public NumericWheelAdapterExtend(Date startDate, Date endDate) {
		mCalenadr = Calendar.getInstance();
		start = Calendar.getInstance();
		start.setTime(startDate);
		end = Calendar.getInstance();
		end.setTime(endDate);
		this.startDate = startDate;
		this.endDate = endDate;
		startDay = start.get(Calendar.DAY_OF_YEAR);
	}

	@Override
	public Object getItem(int index) {
		if (index >= 0 && index < getItemsCount()) {
			int value = startDay + index;
			start.setTime(startDate);
			start.set(Calendar.DAY_OF_YEAR,value);
			long time = start.getTimeInMillis();
			if(time>end.getTimeInMillis()){
				return new Data(end.getTime());
			}
			return new Data(start.getTime());
		}
		return null;
	}

	@Override
	public int getItemsCount() {
		return differentDays(startDate,endDate)+1;
	}

	private  int differentDays(Date date1,Date date2)
	{
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1= cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if(year1 != year2)   //不同年
		{
			int timeDistance = 0 ;
			int min = Math.min(year1,year2);
			int max =  Math.max(year1,year2);
			for(int i = min ; i < max; i ++)
			{
				if(i%4==0 && i%100!=0 || i%400==0)    //闰年
				{
					timeDistance += 366;
				}
				else    //不是闰年
				{
					timeDistance += 365;
				}
			}
			return timeDistance + Math.abs((day2-day1)) ;
		}
		else //同一年
		{
			return  Math.abs((day2-day1));
		}
	}
	
	@Override
	public int indexOf(Object o){
		try {
			if(o!=null &&o instanceof Data){
				Date date = ((Data)o).getTime();
				return differentDays(startDate,date);
			}
			return -1;
		} catch (Exception e) {
			return -1;
		}

	}

	public static class Data implements IPickerViewData{
		private Date time ;
		public Data(Date time){
			this.time = time;
		}
		@Override
		public String getPickerViewText() {
			return getTime(time);
		}
		SimpleDateFormat format = new SimpleDateFormat("MM月dd日" );

		private String getTime(Date date){
			String str_ = format.format(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int week = calendar.get(Calendar.DAY_OF_WEEK);
			String str_week = getWeek(week);

			return str_+" "+str_week;
		}

		public Date getTime(){
			return time;
		}

		private String getWeek(int week) {
			switch (week){
				case Calendar.MONDAY:
					return "周一";
				case Calendar.TUESDAY:
					return "周二";
				case Calendar.WEDNESDAY:
					return "周三";
				case Calendar.THURSDAY:
					return "周四";
				case Calendar.FRIDAY:
					return "周五";
				case Calendar.SATURDAY:
					return "周六";
				case Calendar.SUNDAY:
					return "周日";
			}
			return "";
		}
	}
}
