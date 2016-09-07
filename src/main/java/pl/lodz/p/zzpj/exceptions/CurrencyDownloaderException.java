package pl.lodz.p.zzpj.exceptions;

public class CurrencyDownloaderException extends ApplicationException {
    private static final String NO_RATES_FOR_DATE = "CURRENCY.EXCEPTION.NO_RATES_FOR_DATE";

    public CurrencyDownloaderException() {
    }

    public CurrencyDownloaderException(String message) {
        super(message);
    }

    public CurrencyDownloaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public static CurrencyDownloaderException noRatesForDate() {
        return new CurrencyDownloaderException(NO_RATES_FOR_DATE);
    }
}
