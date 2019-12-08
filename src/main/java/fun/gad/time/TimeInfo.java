package fun.gad.time;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.hateoas.RepresentationModel;

import java.util.Map;

@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeInfo extends RepresentationModel {



    private String message;
    private Map<String, String> timeSupport;
    private int matrix;

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

    public int getMatrix() {
        return matrix;
    }

    public void setMatrix(int matrix) {
        this.matrix = matrix;
    }
}
