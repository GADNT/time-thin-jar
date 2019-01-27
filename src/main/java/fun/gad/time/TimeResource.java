package fun.gad.time;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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
                                                       @Valid @PathVariable(value = "year") Integer year) {

        BadTimeInput badTimeInput = new BadTimeInput(startDate, year).invoke();
        if (badTimeInput.is()) return ResponseEntity.badRequest().body(badTimeInput.getTimeInfo());

        TimeInfo supportDates = timeObject.calculateSupportDates(startDate, year);

        supportDates.add(linkTo(methodOn(TimeResource.class).processSupportDate(startDate,year)).withSelfRel());

        return ResponseEntity.ok(supportDates);
    }

    private class BadTimeInput {
        private boolean myResult;
        private String startDate;
        private int year;
        private TimeInfo timeInfo;

        public BadTimeInput(String startDate, int year) {
            this.startDate = startDate;
            this.year = year;
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

            LocalDate date = LocalDate.now();
            if (year <= 0) {
                timeInfo = new TimeInfo("Year should be positive!");

                myResult = true;
                return this;
            } else if (year < date.getYear()) {
                timeInfo = new TimeInfo("Year should be at least the current year!");

                myResult = true;
                return this;
            }

            myResult = false;
            return this;
        }
    }
}
