package pl.lodz.p.zzpj.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomePageController {
    private static final String WELCOME = "welcomePage";

    @RequestMapping(method = RequestMethod.GET)
    public String welcome() {
        return WELCOME;
    }
}
