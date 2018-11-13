package feup.cmpv.feup.casadamusica.services;

import com.android.volley.Response;

import org.json.JSONObject;

public class OrderServices {

    public static void GetOrders(Response.Listener<JSONObject> success, Response.ErrorListener fail) {
        Api.Get("/orders", success, fail);
    }
}
