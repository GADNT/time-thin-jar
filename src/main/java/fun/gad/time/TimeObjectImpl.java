package fun.gad.time;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
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

                LocalDate week = nextMonday.plusDays(6);
                supportDateMap.put(nextMonday.getDayOfMonth() + ":" + nextMonday.getMonth() + ":" + nextMonday.getYear(), week.getDayOfMonth() + ":" + week.getMonth() + ":" + week.getYear());
                nextMonday = week.plusDays(1).plusWeeks(3);

            } else {
                timeInfo.setMessage("It's not a Monday! Try another day!");

                log.info("It's a beautiful day since is not Monday!");
            }
        }

        timeInfo.setTimeSupport(supportDateMap);
        return timeInfo;
    }

}