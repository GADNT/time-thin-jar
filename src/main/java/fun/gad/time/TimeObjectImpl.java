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
                        sunday.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH), String.valueOf(sunday.getYear()));

                String currentDay = String.join(":", String.valueOf(nextMonday.getDayOfMonth()),
                        nextMonday.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH), String.valueOf(nextMonday.getYear()));

                supportDateMap.put(currentDay, nextDay);
                nextMonday = getNextMondayAroundDate(sunday).plusWeeks(3);

            } else {
                nextMonday = getNextMondayAroundDate(nextMonday);
                timeInfo.setMessage("It's not a Monday! Next Monday is: " + nextMonday.getDayOfMonth());
                log.info("It's a beautiful day since is not Monday!");
            }
        }

        timeInfo.setTimeSupport(supportDateMap);
        return timeInfo;
    }

    private LocalDate getNextMondayAroundDate(LocalDate nextMonday) {
        return nextMonday.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
    }

}