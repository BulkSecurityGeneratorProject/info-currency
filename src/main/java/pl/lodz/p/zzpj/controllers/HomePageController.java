package pl.lodz.p.zzpj.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * REST controller for Home Page
 */
@Controller
@RequestMapping("/")
public class HomePageController {
    Logger logger = Logger.getLogger(HomePageController.class);
    private static final String WELCOME = "welcomePage";

    /**
     * Maps / endpoint.
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String welcome() {
        logger.info("welcome invoked");
        return WELCOME;
    }
}
