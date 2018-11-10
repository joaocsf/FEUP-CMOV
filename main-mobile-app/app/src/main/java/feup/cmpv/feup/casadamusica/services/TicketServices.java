package feup.cmpv.feup.casadamusica.services;

import com.android.volley.Response;

import org.json.JSONObject;

public class TicketServices {
    public static void BuyTicket(JSONObject objectRequest, Response.Listener<JSONObject> success, Response.ErrorListener fail){
        Api.Post("/ticket/buy", objectRequest, success, fail);
    }

    public static void GetTickets(String costumerUuid, Response.Listener<JSONObject> success, Response.ErrorListener fail){
        Api.Get("/ticket/costumer/" + costumerUuid  , success, fail);
    }
}
