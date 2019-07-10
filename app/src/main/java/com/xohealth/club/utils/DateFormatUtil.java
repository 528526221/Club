package com.xohealth.club.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Desc :
 * Created by xulc on 2018/12/16.
 */
public class DateFormatUtil {
    public static final String FORMAT_DATE_YEAR_MONTH_DAY = "yyyy-MM-dd";

    public static String getFormatDateYearMonthDay(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_YEAR_MONTH_DAY);
        return sdf.format(date);
    }


}
