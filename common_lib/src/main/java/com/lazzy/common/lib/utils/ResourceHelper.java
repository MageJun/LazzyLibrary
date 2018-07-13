package com.lazzy.common.lib.utils;

import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.TextView;

import com.lazzy.common.lib.GlobalInit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ResourceHelper {

    public static String getStr(int rid){
        return GlobalInit.appContext().getResources().getString(rid);
    }
    public static String[] getStrArray(int rid){
        return GlobalInit.appContext().getResources().getStringArray(rid);
    }

    public static String getStr(int rid, Object... params){
        return GlobalInit.appContext().getResources().getString(rid,params);
    }



    public static int getColor(int colorRid){
        return GlobalInit.appContext().getResources().getColor(colorRid);
    }

    public static int getDimen(int dimen){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dimen,GlobalInit.appContext().getResources().getDisplayMetrics());
    }
    public static int getTextDimen(int dimen){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,dimen,GlobalInit.appContext().getResources().getDisplayMetrics());
    }

    public static void setTextSize(TextView tv, int size){
        if(tv!=null){
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
        }
    }

   static SimpleDateFormat sDateFormat = new SimpleDateFormat("MM月dd日 HH:mm");
    static SimpleDateFormat sDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String getDateStr(long time){
        return sDateFormat.format(new Date(time));
    }

    public static long getTime(String timeStr){
        if(!TextUtils.isEmpty(timeStr)){
            try {
                Date date =sDateFormat2.parse(timeStr);
                return date.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }


    /**
     * 根据毫秒时间戳来格式化字符串
     * 今天显示今天、昨天显示昨天、前天显示前天.
     * 早于前天的显示具体年-月-日，如2017.06.12；
     * @param timeStamp 毫秒值
     * @return 今天 昨天 前天  MM.dd HH:mm或者 yyyy.MM.dd HH:mm类型字符串
     */
    public static String getFormatTimeStr(long timeStamp) {
        SimpleDateFormat tmpFormat = new SimpleDateFormat("HH:mm");
        long curTimeMillis = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(curTimeMillis);

        int todayHoursSeconds = calendar.get(Calendar.HOUR_OF_DAY)*60*60;
        int todayMinutesSeconds =  calendar.get(Calendar.MINUTE)*60;
        int todaySeconds = calendar.get(Calendar.SECOND);
        int todayMillis = (todayHoursSeconds + todayMinutesSeconds + todaySeconds) * 1000;
        long todayStartMillis = curTimeMillis - todayMillis;
        if(timeStamp >= todayStartMillis) {
            return "今天"+" "+tmpFormat.format(new Date(timeStamp));
        }
        int oneDayMillis = 24 * 60 * 60 * 1000;
        long yesterdayStartMilis = todayStartMillis - oneDayMillis;
        if(timeStamp >= yesterdayStartMilis) {
            return "昨天"+" "+tmpFormat.format(new Date(timeStamp));
        }
        long yesterdayBeforeStartMilis = yesterdayStartMilis - oneDayMillis;
        if(timeStamp >= yesterdayBeforeStartMilis) {
            return "前天"+" "+tmpFormat.format(new Date(timeStamp));
        }
        if(timeStamp>=getYearTimeDate(timeStamp)){
            SimpleDateFormat sdf = new SimpleDateFormat("MM.dd HH:mm");
            return  sdf.format(new Date(timeStamp));
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        return  sdf.format(new Date(timeStamp));

    }

    /**
     * 返回指定时间戳的本周起始时间戳
     * @param timeSample
     * @return
     */
    public static long getWeekTimeDate(long timeSample){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_WEEK,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        Date date = calendar.getTime();
        return date.getTime();
    }

    /**
     * 返回指定时间戳的月份起始时间戳
     * @param timeSample
     * @return
     */
    public static long getMonthTimeDate(long timeSample){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        Date date = calendar.getTime();
        return date.getTime();
    }

    /**
     * 获取指定时间戳所在年份的起始时间戳
     * @param timeSample
     * @return
     */
    public static long getYearTimeDate(long timeSample){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_YEAR,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        Date date = calendar.getTime();
        return date.getTime();
    }
    /**
     * 获取指定时间戳所在年份的结束时间戳
     * @param timeSample
     * @return
     */
    public static long getYearEndTimeDate(long timeSample){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_YEAR,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.add(Calendar.YEAR,1);
        calendar.add(Calendar.MILLISECOND,-1);
        Date date = calendar.getTime();
        return date.getTime();
    }

    /**
     * 获取两个Date之间相隔的日期天数
     * @param date1
     * @param date2
     * @return
     */
    public static  int differentDays(Date date1, Date date2)
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

    /**
     * 返回同一天的间隔小时
     * @param date1
     * @param date2
     * @return
     */
    public static int differentHours(Date date1, Date date2){
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int hour1= cal1.get(Calendar.HOUR_OF_DAY);
        int hour2 = cal2.get(Calendar.HOUR_OF_DAY);

        return Math.abs(hour1-hour2);
    }
    /**
     * 返回同一天的间隔小时
     * @param date1
     * @param date2
     * @return
     */
    public static int differentMinutes(Date date1, Date date2){
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int m1= cal1.get(Calendar.MINUTE);
        int m2 = cal2.get(Calendar.MINUTE);

        return Math.abs(m1-m2);
    }


    public static float getFloatByStr(String str){
        if(!TextUtils.isEmpty(str)){
            return Float.valueOf(str);
        }
        return -1f;
    }

    public static double getRadio(double molecule,double denominator){
        return molecule/denominator;
    }
}
