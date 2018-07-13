package com.helper;

import android.util.Log;

/**
 *
 * %w 星期, 0-6 (0是星期天)
 %W 算出某一天属于该年的第几周, 01-53

 SELECT strftime('%s','now');
 SELECT date('%s','now','start of week');//start of week报错，需要换种方式
 SELECT * FROM `tab_time` WHERE `time` BETWEEN  strftime('%s','now') AND strftime('%s','now','start of week')

 select * from tab_time where time between strftime('now', 'start of day',(-strftime('%w','now'))||' day','1 day') and  strftime('%s','now');
 * 提供获取特定数字的语句
 */
public class SqliteHelper {
    public static final String WEEK_START = "strftime('%s','now', 'start of day',(-strftime('%w','now'))||' day','1 day') ";
    public static final String WEEK_LAST_END = "strftime('%s','now', 'start of day',(-strftime('%w','now'))||' day','1 day','-1 second') ";
    public static final String WEEK_LAST_START= "strftime('%s','now', 'start of day',(-strftime('%w','now'))||' day','-6 day') ";
    public static final String NOW = " strftime('%s','now')";

    private static  String OFFSET_DAY = " day')";
    private static  String DELAY_DAY_BEFORE_NOW = "select datetime('now', 'start of day','";

    /**
     * 跟当前时间间隔天数的起始时间
     * @param offset
     * @param isAfter
     * @return
     */
    public static String getOffsetDayStart(int offset,boolean isAfter){
        if(offset<0){
            return null;
        }
        if(!isAfter){
            offset*=-1;
        }
        String sql =  DELAY_DAY_BEFORE_NOW+offset+OFFSET_DAY;
        Log.i("sqltrace","sql = "+sql);
        return sql;
    }
}
