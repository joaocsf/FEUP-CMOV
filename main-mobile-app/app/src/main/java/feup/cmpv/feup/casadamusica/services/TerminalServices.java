package feup.cmpv.feup.casadamusica.services;

import com.android.volley.Response;

import org.json.JSONObject;

public class TerminalServices {

    public static void ValidateTicket(JSONObject objectRequest, Response.Listener<JSONObject> success, Response.ErrorListener fail){
        Api.Post("/ticket/validation", objectRequest, success, fail);
    }
}
