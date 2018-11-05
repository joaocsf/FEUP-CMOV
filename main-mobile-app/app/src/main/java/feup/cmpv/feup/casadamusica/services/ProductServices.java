package feup.cmpv.feup.casadamusica.services;

import com.android.volley.Response;

import org.json.JSONObject;

public class ProductServices {

    public static void GetProducts(Response.Listener<JSONObject> success, Response.ErrorListener fail) {
        Api.Get("/products", success, fail);
    }
}
