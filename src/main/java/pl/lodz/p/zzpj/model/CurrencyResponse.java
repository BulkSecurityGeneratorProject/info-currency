package pl.lodz.p.zzpj.model;

/**
 * Created by piotr on 02.09.16.
 */
public class CurrencyResponse {
    Object data;
    int statusCode;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Object getData() {

        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
