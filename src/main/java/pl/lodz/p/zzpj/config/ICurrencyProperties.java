package pl.lodz.p.zzpj.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Properties specific to Info-Currency app.
 *
 * <p>
 *     Properties are configured in the application.yml file.
 * </p>
 */
@ConfigurationProperties(prefix = "icurrency", ignoreUnknownFields = false)
public class ICurrencyProperties {
    private final Mail mail = new Mail();

    private final Security security = new Security();

    public Mail getMail() {
        return mail;
    }

    public Security getSecurity() {
        return security;
    }

    public static class Mail {

        private String from = "icurrency@localhost";

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }
    }

    public static class Security {

        private final Authentication authentication = new Authentication();

        public Authentication getAuthentication() {
            return authentication;
        }

        public static class Authentication {

            private final Jwt jwt = new Jwt();

            public Jwt getJwt() {
                return jwt;
            }

            public static class Jwt {

                private String secret;

                private long tokenValidityInSeconds = 1800;
                private long tokenValidityInSecondsForRememberMe = 2592000;

                public String getSecret() {
                    return secret;
                }

                public void setSecret(String secret) {
                    this.secret = secret;
                }

                public long getTokenValidityInSeconds() {
                    return tokenValidityInSeconds;
                }

                public void setTokenValidityInSeconds(long tokenValidityInSeconds) {
                    this.tokenValidityInSeconds = tokenValidityInSeconds;
                }

                public long getTokenValidityInSecondsForRememberMe() {
                    return tokenValidityInSecondsForRememberMe;
                }

                public void setTokenValidityInSecondsForRememberMe(long tokenValidityInSecondsForRememberMe) {
                    this.tokenValidityInSecondsForRememberMe = tokenValidityInSecondsForRememberMe;
                }
            }
        }
    }
}
