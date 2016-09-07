package pl.lodz.p.zzpj.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by piotr on 07.09.16.
 */
@Controller
public class LoginController {
    private static final String LOGIN_VIEW = "login";
    private static final String ACCOUNT_VIEW = "account";

    @RequestMapping("login")
    public String getLoginPage() {
        return LOGIN_VIEW;
    }

    @RequestMapping("account")
    public String getAccountPage() {
        return ACCOUNT_VIEW;
    }
}
