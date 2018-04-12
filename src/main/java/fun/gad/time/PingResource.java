package fun.gad.time;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "ping")
public class PingResource {


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String ping(){
        return "OK";
    }
}
