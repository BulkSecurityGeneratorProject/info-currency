package pl.lodz.p.zzpj.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by piotr on 07.09.16.
 */
@Controller
public class LoginController {
    public static final String LOGIN_VIEW = "login";

    @RequestMapping("login")
    public String getLoginPage(){
        return LOGIN_VIEW;
    }
}
