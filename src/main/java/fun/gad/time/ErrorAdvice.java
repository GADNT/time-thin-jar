package fun.gad.time;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorAdvice {

    //todo: change this with error message class
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<TimeInfo> handleBadRequest() {
        TimeInfo timeInfo = new TimeInfo("Date or year not in an accepted formats!");
        return ResponseEntity.badRequest().body(timeInfo);
    }
}
