package pl.lodz.p.zzpj.security;

import pl.lodz.p.zzpj.config.Constants;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import pl.lodz.p.zzpj.security.SecurityUtils;

/**
 * Implementation of AuditorAware based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        String userName = SecurityUtils.getCurrentUserLogin();
        return (userName != null ? userName : Constants.SYSTEM_ACCOUNT);
    }
}
