package fun.gad.time;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import static fun.gad.time.constants.TimeContants.DATE_FORMAT;
import static java.time.DayOfWeek.MONDAY;

@Service
public class TimeObjectImpl {
    private static Logger log = LoggerFactory.getLogger(TimeObjectImpl.class);

    public TimeInfo calculateSupportDates(String starDate, int year) {
        TimeInfo timeInfo = new TimeInfo();

        Map<String, String> supportDateMap = new LinkedHashMap<>();
        LocalDate date = LocalDate.parse(starDate, DateTimeFormatter.ofPattern(DATE_FORMAT));

        LocalDate nextMonday = date;

        while (nextMonday.getYear() <= year) {

            if (nextMonday.getDayOfWeek().equals(MONDAY)) {

                LocalDate sunday = nextMonday.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

                String nextDay = String.join(":", String.valueOf(sunday.getDayOfMonth()),
                        sunday.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toUpperCase(), String.valueOf(sunday.getYear()));

                String currentDay = String.join(":", String.valueOf(nextMonday.getDayOfMonth()),
                        nextMonday.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toUpperCase(), String.valueOf(nextMonday.getYear()));

                supportDateMap.put(currentDay, nextDay);
                nextMonday = getNextMondayAroundDate(sunday).plusWeeks(2);

            } else {
                nextMonday = getNextMondayAroundDate(nextMonday);
                timeInfo.setMessage("It's not a Monday! Next Monday is: " + nextMonday.getDayOfMonth());
                log.info("It's a beautiful day since is not Monday!");
            }
        }
timeInfo.setMatrix(sumMatrixElementsOneIteration());
        timeInfo.setTimeSupport(supportDateMap);
        return timeInfo;
    }

    private LocalDate getNextMondayAroundDate(LocalDate nextMonday) {
        return nextMonday.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
    }

/*
    private static int sumMatrixElements(){

        int rez = 0;
        int[][] multi = new int[][]{
                { 2, 3, 4, 5, 6 },
                { 3, 4, 5, 6, 7 },
                { 4, 5, 6, 7, 8 },
                { 5, 6, 7, 8, 9 },
                { 6, 7, 8, 9, 10 }
        };

        for (int i = 0; i < multi.length; i++) {
            for (int j = 0; j < multi.length; j++) {
                System.out.println("matrix  :  "+ i + " <-> " + j + "rez: " + rez);
                rez += multi[i][j];

            }
        }

        return rez;

    }*/


   private static int sumMatrixElementsOneIteration(){

        int rezit = 0;
        int[][] multi = new int[][]{
                { 2, 3, 4, 5, 6 },
                { 3, 4, 5, 6, 7 },
                { 4, 5, 6, 7, 8 },
                { 5, 6, 7, 8, 9 },
                { 6, 7, 8, 9, 10 }
        };

        for (int i = 0; i < multi.length; i++) {
            int c = i;
//            for (int j = 0; j < multi.length; j++) {
            if(c<i) {
                System.out.println("matrix  :  " + i + " <-> " + c + "rez: " + rezit);
                rezit += multi[i][c];
                c++;
            }
//            }
        }

        return rezit;

    }
}