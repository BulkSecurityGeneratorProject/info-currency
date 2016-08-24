package pl.lodz.p.zzpj.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
@RequestMapping("/welcome")
public class GuestController {
    private static final String WELCOME = "welcomePage";

    @RequestMapping(method = RequestMethod.GET)
    public String welcome(Map<String, Object> attributes) {
        return WELCOME;
    }
}
