package feup.cmpv.feup.casadamusica.services;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class TerminalServices {

    public static void ValidateTicket(JSONObject objectRequest, Response.Listener<JSONObject> success, Response.ErrorListener fail){
        Api.Post("/ticket/validation", objectRequest, success, fail);
    }

    public static void Order(JSONObject objectRequest, Response.Listener<JSONObject> success, Response.ErrorListener fail){
         Api.Post("/order", objectRequest, success, fail);
    }

    public static void Login(String identifier, String password, Response.Listener<JSONObject> success, Response.ErrorListener fail){
        try {
            JSONObject obj = new JSONObject();
            obj.put("identifier", identifier);
            obj.put("password", password);
            Api.Post("/terminal", obj, success, fail);
        } catch (Exception e){
        }
    }
}
