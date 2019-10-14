package com.violin.test.java8.time.simpformat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author guo.lin  2019/7/18
 */
public class SimpleDateFormatUtil {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static String format(Date date) throws ParseException {
        return sdf.format(date);
    }

    public static Date parse(String strDate) throws ParseException {
        return sdf.parse(strDate);
    }

    public static class SimpleDateFormatThread extends Thread {

        private String dateStr;

        public SimpleDateFormatThread(String dateStr) {
            this.dateStr = dateStr;
        }

        @Override
        public void run() {
            try {
                System.out.println(this.getName() + ":" + SimpleDateFormatUtil.parse(dateStr));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String dateStr = "2018-11-03 10:02:47";
        for (int i = 0; i < 10000; i++) {
            new SimpleDateFormatThread(dateStr).start();
        }
    }}
