package fun.gad.time;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
    private static Logger log = LoggerFactory.getLogger(ErrorController.class);

    @GetMapping(value = "/error",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ErrorInfo> handleError(Exception e) {

        log.error("Error occurred : {}", e);
        String message = e.getMessage();
        ErrorInfo errorInfo = new ErrorInfo(message);
        return ResponseEntity.ok().body(errorInfo);
    }
}
