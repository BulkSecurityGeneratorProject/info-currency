package pl.lodz.p.zzpj.config;

/**
 * Application constants.
 * <p>
 */
public final class Constants {
    // Spring profiles for development and production
    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";

    //Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";

    private Constants() {
    }
}
