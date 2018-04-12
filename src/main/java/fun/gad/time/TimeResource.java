package fun.gad.time;


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


    @GetMapping(value = "/{locale}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> processingTime(@PathVariable(value = "locale") String locale){

        LocalDateTime currentTimeBucharest = LocalDateTime.now(ZoneId.of("Europe/Bucharest"));
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(new Locale(locale));
        String dateTime = currentTimeBucharest.format(formatter);

        return ResponseEntity.ok(dateTime);

    }

}
