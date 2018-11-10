package feup.cmpv.feup.casadamusica.services;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import feup.cmpv.feup.casadamusica.structures.Card;
import feup.cmpv.feup.casadamusica.structures.Costumer;

public class OrderServices {

    public static void Order(String order, String verification, Response.Listener<JSONObject> success, Response.ErrorListener fail){
        try {
            JSONObject object = new JSONObject();
            object.put("order", order);
            object.put("validation", verification);

            Api.Post("/order", object, success, fail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
