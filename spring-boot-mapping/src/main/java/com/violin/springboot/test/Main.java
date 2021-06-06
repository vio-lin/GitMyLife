package com.violin.springboot.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws ParseException {
        Map<String, Plant> plantConfig = new HashMap();
        Calendar earthCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        earthCalendar.set(2099, Calendar.OCTOBER, 1, 8, 0, 0);

        long day = 1329L;
        long hour = 18L;
        long timePass = TimeUnit.DAYS.toMillis(day) + TimeUnit.HOURS.toMillis(hour);
        plantConfig.put("Kerbin", new Plant(1.0, 420));
        plantConfig.put("Moho", new Plant(15, 6));
        plantConfig.put("Eve", new Plant(5, 48));
        plantConfig.put("Duna", new Plant(2, 342));
        plantConfig.put("Dres", new Plant(0.25, 5844));
        plantConfig.put("Eeloo", new Plant(6, 6804));
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            Plant plant = plantConfig.get(scanner.nextLine());
            if (plant == null) {
                throw new IllegalArgumentException("can not find plant with name :" + plant);
            }

            long time = earthCalendar.getTimeInMillis();
            long earthTime = (time + timePass);

            long timeNow = (long) (earthTime / plant.getSecondInEarth()) / 1000;

            // 秒
            long second = timeNow % plant.getSecondsOfMinute();
            // 分
            long minuteCounts = timeNow / plant.getSecondsOfMinute();
            long minuteInHour = (minuteCounts) % plant.getMinutesOfHour();
            // 小时
            long hourCount = minuteCounts / plant.getMinutesOfHour();
            long hourInDay = hourCount % plant.getHourOfDay();
            // 日
            long dayCount = hourCount / plant.getHourOfDay();
            long dayInMonth = dayCount % plant.getDayOfMonth();
            // 月
            long monthCount = dayCount / plant.getDayOfMonth();
            long monthOfyear = monthCount % 6;
            // 年
            long year = monthCount / 6;
            System.out.println(format(year, monthOfyear, dayInMonth, hourInDay, minuteInHour, second));
        }
    }

    private static String format(long year, long monthOfyear, long dayInMonth, long hourInDay, long minuteInHour,
        long second) {
        return String
            .format("%d-%d-%d %d:%d:%d", year, monthOfyear + 1, dayInMonth + 1, hourInDay, minuteInHour, second);
    }

    public static class Plant {
        private long hourOfDay = 10L;
        private long minutesOfHour = 10L;
        private long secondsOfMinute = 50;
        private long monthsOfYear = 6;
        private double secondInEarth;
        private long dayOfMonth;

        public Plant(double dayOfEarth, long dayOfyear) {
            this.secondInEarth = dayOfEarth * 17.28;
            this.dayOfMonth = dayOfyear / monthsOfYear;
        }

        public long getHourOfDay() {
            return hourOfDay;
        }

        public void setHourOfDay(long hourOfDay) {
            this.hourOfDay = hourOfDay;
        }

        public long getMinutesOfHour() {
            return minutesOfHour;
        }

        public void setMinutesOfHour(long minutesOfHour) {
            this.minutesOfHour = minutesOfHour;
        }

        public long getSecondsOfMinute() {
            return secondsOfMinute;
        }

        public void setSecondsOfMinute(long secondsOfMinute) {
            this.secondsOfMinute = secondsOfMinute;
        }

        public long getMonthsOfYear() {
            return monthsOfYear;
        }

        public void setMonthsOfYear(long monthsOfYear) {
            this.monthsOfYear = monthsOfYear;
        }

        public double getSecondInEarth() {
            return secondInEarth;
        }

        public void setSecondInEarth(double secondInEarth) {
            this.secondInEarth = secondInEarth;
        }

        public long getDayOfMonth() {
            return dayOfMonth;
        }

        public void setDayOfMonth(long dayOfMonth) {
            this.dayOfMonth = dayOfMonth;
        }
    }
}
