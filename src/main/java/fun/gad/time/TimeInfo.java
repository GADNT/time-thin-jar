package fun.gad.time;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.hateoas.ResourceSupport;

import java.util.Map;

@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeInfo extends ResourceSupport {



    private String message;
    private Map<String, String> timeSupport;

    public TimeInfo() {
    }

    public TimeInfo(String message){
        this.message = message;
    }

    @JsonGetter(value = "busy")
    public Map<String, String> getTimeSupport() {
        return timeSupport;
    }

    public void setTimeSupport(Map<String, String> timeSupport) {
        this.timeSupport = timeSupport;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
