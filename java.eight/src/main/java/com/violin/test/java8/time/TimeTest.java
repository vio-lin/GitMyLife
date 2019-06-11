package com.violin.test.java8.time;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.Locale;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

/**
 * @author guo.lin  2019/6/10
 */
public class TimeTest {
    public static void main(String[] args) {
        Date date = new Date(2019,06,10);
        System.out.println(date);

        System.out.println("********************LocalDate*********************************");
        LocalDate today = LocalDate.now();
        LocalDate localDate = LocalDate.of(2018,05 ,10 );
        int year = today.get(ChronoField.YEAR);
        LocalDate dateString = LocalDate.parse("2014-03-18");
        System.out.println(localDate.getDayOfMonth());

        System.out.println("********************LocalTime*********************************");
        LocalTime time = LocalTime.of(13, 45, 20);
        int hour = time.getHour();
        int minute = time.getMinute();
        int second = time.getSecond();
        LocalTime timeString = LocalTime.parse("13:45:20");


        System.out.println("********************LocalDateTime*********************************");
        LocalDateTime dt1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20);
        LocalDate date1 = dt1.toLocalDate();
        LocalTime time1 = dt1.toLocalTime();

        System.out.println("********************Duration*********************************");
        LocalTime time2 = LocalTime.of(13,12 );
        Duration d1 = Duration.between(time1, time2);
        LocalDateTime dateTime1 = LocalDateTime.of(2019,06 , 10,9 , 30);
        LocalDateTime dateTime2 = LocalDateTime.of(2019,07 , 10,9 , 30);
        Duration d2 = Duration.between(dateTime1, dateTime2);
        Instant instant1 = Instant.now();
        Instant instant2 = Instant.now();
        Duration d3 = Duration.between(instant1, instant2);

        System.out.println("********************Period*********************************");
        Period tenDays = Period.between(LocalDate.of(2014, 3, 8),LocalDate.of(2014, 3, 18));
        Period ten1Days = Period.ofDays(10);
        Period threeWeeks = Period.ofWeeks(3);
        Period twoYearsSixMonthsOneDay = Period.of(2, 6, 1);

        System.out.println("********************TemporalAdjuster*********************************");
        LocalDate samp = LocalDate.of(2014, 3, 18);
        LocalDate adjuster1 = samp.with(nextOrSame(DayOfWeek.SUNDAY));
        LocalDate adjuster2 = samp.with(lastDayOfMonth());
        LocalDate timeAdjustTaskResult = samp.with(new TimeAdjustTask());

        System.out.println("********************DateTimeFormatter*********************************");
        //线程安全
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate1 = LocalDate.of(2014, 3, 18);
        String formattedDate = localDate1.format(formatter);
        LocalDate date2 = LocalDate.parse(formattedDate, formatter);

        DateTimeFormatter italianFormatter =
                DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.ITALIAN);

        DateTimeFormatter zhFormatter = new DateTimeFormatterBuilder()
                .appendText(ChronoField.DAY_OF_MONTH)
                .appendLiteral(". ")
                .appendText(ChronoField.MONTH_OF_YEAR)
                .appendLiteral(" ")
                .appendText(ChronoField.YEAR)
                .parseCaseInsensitive()
                .toFormatter(Locale.CHINA);


    }
}
