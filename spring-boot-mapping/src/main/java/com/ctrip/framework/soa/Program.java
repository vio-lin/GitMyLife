package com.ctrip.framework.soa;

import java.util.Calendar;
import java.util.TimeZone;

@SuppressWarnings("all")
public class Program {

    public static void main(String[] args) {
        Calendar earthCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        earthCalendar.set(2099, Calendar.OCTOBER, 1, 8, 0, 0);
        long departTimestampInSeconds = earthCalendar.getTimeInMillis() / 1000;
        long duratrionInSeconds = (1329 * 24 + 18 ) * 3600;
        long arriveTimestampInSeconds = departTimestampInSeconds + duratrionInSeconds;
        String[] planets = new String[] { "Kerbin", "Moho", "Eve", "Duna", "Dres", "Eeloo" };
        for (String planet : planets) {
            AbstractCalendar calendar = CalendarFactory.newCalendar(planet, arriveTimestampInSeconds);
            System.out.println(calendar);
        }
    }

    private static class CalendarFactory {
        static AbstractCalendar newCalendar(String name, long arriveTimestampInSeconds) {
            switch (name) {
                case "Kerbin": return new KerbinCalendar(arriveTimestampInSeconds);
                case "Moho": return new MohoCalendar(arriveTimestampInSeconds);
                case "Eve": return new EveCalendar(arriveTimestampInSeconds);
                case "Duna": return new DunaCalendar(arriveTimestampInSeconds);
                case "Dres": return new DresCalendar(arriveTimestampInSeconds);
                case "Eeloo": return new EelooCalendar(arriveTimestampInSeconds);
                default: throw new IllegalArgumentException("Unkonwn Planet.");
            }
        }
    }

    private static abstract class AbstractCalendar {

        private final long timestampInSeconds;
        private final long year;
        private final long month;
        private final long day;
        private final long hour;
        private final long minute;
        private final long second;

        AbstractCalendar(long timestampInSeconds) {
            this.timestampInSeconds = timestampInSeconds;
            double localSecondDuration = 1.0 * rotationInEarthDay() * 86400 / (hoursEveryDay() * minutesEveryHour() * secondsEveryMinute());
            long totalSeconds = (long) (timestampInSeconds / localSecondDuration);
            this.second = totalSeconds % secondsEveryMinute();

            long totalMinutes = totalSeconds / secondsEveryMinute();
            this.minute = totalMinutes % minutesEveryHour();

            long totalHours = totalMinutes / minutesEveryHour();
            this.hour = totalHours % hoursEveryDay();

            long totalDays = totalHours / hoursEveryDay();
            this.day = totalDays % daysEveryMonth();

            long totalMonths = totalDays / daysEveryMonth();
            this.month = totalMonths % monthsEveryYear();

            this.year = totalMonths / monthsEveryYear();
        }

        public long getTimestampInSeconds() {
            return timestampInSeconds;
        }

        public long getYear() {
            return year;
        }

        public long getMonth() {
            return month + 1;
        }

        public long getDay() {
            return day + 1;
        }

        public long getHour() {
            return hour;
        }

        public long getMinute() {
            return minute;
        }

        public long getSecond() {
            return second;
        }

        @Override
        public String toString() {
            return String.format("%d-%d-%d %d:%d:%d", getYear(), getMonth(), getDay(), getHour(), getMinute(), getSecond());
        }

        // 公转所需地球日
        abstract int revolutionInEarthDay();

        // 自转所需地球日
        abstract double rotationInEarthDay();

        int monthsEveryYear() {
            return 6;
        }

        int daysEveryMonth() {
            return revolutionInEarthDay() / monthsEveryYear();
        }

        int hoursEveryDay() {
            return 10;
        }

        int minutesEveryHour() {
            return 10;
        }

        int secondsEveryMinute() {
            return 50;
        }
    }

    private static class KerbinCalendar extends AbstractCalendar {

        KerbinCalendar(long timestamp) {
            super(timestamp);
        }

        @Override
        double rotationInEarthDay() {
            return 1;
        }

        @Override
        int revolutionInEarthDay() {
            return 420;
        }
    }

    private static class MohoCalendar extends AbstractCalendar {

        MohoCalendar(long timestamp) {
            super(timestamp);
        }

        @Override
        double rotationInEarthDay() {
            return 15;
        }

        @Override
        int revolutionInEarthDay() {
            return 6;
        }
    }

    private static class EveCalendar extends AbstractCalendar {

        EveCalendar(long timestamp) {
            super(timestamp);
        }

        @Override
        double rotationInEarthDay() {
            return 5;
        }

        @Override
        int revolutionInEarthDay() {
            return 48;
        }
    }

    private static class DunaCalendar extends AbstractCalendar {

        DunaCalendar(long timestamp) {
            super(timestamp);
        }

        @Override
        double rotationInEarthDay() {
            return 2;
        }

        @Override
        int revolutionInEarthDay() {
            return 342;
        }
    }

    private static class DresCalendar extends AbstractCalendar {

        DresCalendar(long timestamp) {
            super(timestamp);
        }

        @Override
        double rotationInEarthDay() {
            return 0.25;
        }

        @Override
        int revolutionInEarthDay() {
            return 5844;
        }
    }

    private static class EelooCalendar extends AbstractCalendar {

        EelooCalendar(long timestamp) {
            super(timestamp);
        }

        @Override
        double rotationInEarthDay() {
            return 6;
        }

        @Override
        int revolutionInEarthDay() {
            return 6084;
        }
    }
}
