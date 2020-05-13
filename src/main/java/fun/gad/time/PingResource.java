package fun.gad.time;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@RestController
@RequestMapping(value = "/")
public class PingResource {


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String ping() {
        return "OK";
    }

    @GetMapping(value = "/quarantine/{startdate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> processQuarantineDate(@Valid @PathVariable(value = "startdate", required = true) String startDate) {
//        String now = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String in = Optional.ofNullable(startDate)
                .filter(s -> s.length() >= 1 && checkFormatAccepted(s))
                .orElse(now);
        return new ResponseEntity<>(prettyFormatDays(now, in), HttpStatus.OK);
    }

    private String prettyFormatDays(String now, String in) {
        long daysBetween = calculateDaysBetween(in, now);
        return "Quarantine : " + daysBetween + " days.";
    }

    private static boolean checkFormatAccepted(String date) {
        boolean formatApproved;
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            formatApproved = true;
        } catch (Exception e) {
            formatApproved = false;
        }
        return formatApproved;
    }

    public static long calculateDaysBetween(String dateThen, String dateNow) {
        System.out.println("startDate = " + dateThen + ", dateNow = " + dateNow);

        LocalDate dateBack = LocalDate.parse(dateThen, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        LocalDate current = LocalDate.parse(dateNow, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        return ChronoUnit.DAYS.between(dateBack, current);

    }
}
