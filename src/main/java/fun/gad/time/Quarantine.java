package fun.gad.time;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quarantine extends RepresentationModel {

    private String message;
    private String days;

    public Quarantine(String message, String days) {
        this.message = message;
        this.days = days;
    }

    public Quarantine(Link initialLink, String days) {
        super(initialLink);
        this.days = days;
    }

    public Quarantine(List initialLinks, String days) {
        super(initialLinks);
        this.days = days;
    }

    public String getDays() {
        return days;
    }

    public String getMessage() {
        return message;
    }
}
