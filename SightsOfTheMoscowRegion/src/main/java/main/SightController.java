package main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.Date;

@Controller
public class SightController {

    @RequestMapping("/")
    public String index() {
        LocalDateTime date = LocalDateTime.now();
        return date.toString();
    }
}
