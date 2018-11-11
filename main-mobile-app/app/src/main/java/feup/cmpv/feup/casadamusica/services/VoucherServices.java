package feup.cmpv.feup.casadamusica.services;

import com.android.volley.Response;

import org.json.JSONObject;

public class VoucherServices {
    public static void GetVouchers(Response.Listener<JSONObject> success, Response.ErrorListener fail){
        Api.Get("/vouchers", success, fail);
    }
}
