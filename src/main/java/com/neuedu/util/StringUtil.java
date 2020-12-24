package com.neuedu.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class StringUtil {

    //生成唯一的id(订单号)
    public static String generateOrderNo(){

        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String now = df.format(new Date());

        Random random = new Random();
        int number = random.nextInt(900)+100;

        return now + number;
    }

    //将数据库中的日期数据转换为自定义的格式
    public static String convertDatetime(Date date) {

        Date now = new Date();

        long seconds = (now.getTime() - date.getTime()) / 1000;

        if(seconds<60){
            return "刚刚发布";
        }else if(seconds<60*60){
            return seconds / 60 + "分钟前发布";
        }else if(seconds<60*60*3) {
            return seconds / 60 / 60 + "小时前发布";
        }else if(isToday(date)) {
            DateFormat df2 = new SimpleDateFormat("HH时mm分");
            return "今天"+ df2.format(date)+"发布";
        }else{
            DateFormat df2 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
            return df2.format(date) + "发布";
        }
    }

    static boolean isToday(Date date){

        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);

        Calendar c2 = Calendar.getInstance();
        int year = c2.get(Calendar.YEAR);
        int month = c2.get(Calendar.MONTH);
        int day = c2.get(Calendar.DAY_OF_MONTH);

        c2.set(year, month, day, 0,0,0);

        return c1.after(c2);

    }



}
