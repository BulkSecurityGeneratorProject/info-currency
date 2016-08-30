package pl.lodz.p.zzpj.exceptions;

public class CurrencyDownloaderException extends ApplicationException {
    private static final String NO_TODAYS_RATES = "CURRENCY.EXCEPTION.NO_TODAYS_RATES";

    public CurrencyDownloaderException() {
    }

    public CurrencyDownloaderException(String message) {
        super(message);
    }

    public CurrencyDownloaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public static CurrencyDownloaderException noTodaysRates() {
        return new CurrencyDownloaderException(NO_TODAYS_RATES);
    }
}
