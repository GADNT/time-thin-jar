package fun.gad.time;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@RestController
@RequestMapping(value = "/time")
public class TimeResource {

//    @Autowired
//    OAuth2RestTemplate oAuth2RestTemplate;

    @Autowired
    TimeObjectImpl timeObject;


    @GetMapping(value = "/{locale}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> processingTime(@PathVariable(value = "locale") String locale) {

        LocalDateTime currentTimeBucharest = LocalDateTime.now(ZoneId.of("Europe/Bucharest"));
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(new Locale(locale));
        String dateTime = currentTimeBucharest.format(formatter);

        return ResponseEntity.ok(dateTime);

    }


    @GetMapping(value = "/support/{startdate}/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TimeInfo> processSupportDate(@PathVariable(value = "startdate") String startDate,
                                                       @PathVariable(value = "year") int year) {

        BadTimeInput badTimeInput = new BadTimeInput(startDate).invoke();
        if (badTimeInput.is()) return ResponseEntity.badRequest().body(badTimeInput.getTimeInfo());

        TimeInfo supportDates = timeObject.calculateSupportDates(startDate, year);

        return ResponseEntity.ok(supportDates);
    }

    private class BadTimeInput {
        private boolean myResult;
        private String startDate;
        private TimeInfo timeInfo;

        public BadTimeInput(String startDate) {
            this.startDate = startDate;
        }

        boolean is() {
            return myResult;
        }

        public TimeInfo getTimeInfo() {
            return timeInfo;
        }

        public BadTimeInput invoke() {
            try {
               DateTimeFormatter.ofPattern("ddMMyyyy").parse(startDate);
            } catch (Exception e) {
                timeInfo = new TimeInfo("Date in wrong format! Try : ddMMyyyy format!");

                myResult = true;
                return this;
            }
            myResult = false;
            return this;
        }
    }
}
