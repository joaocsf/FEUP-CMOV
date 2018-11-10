package feup.cmpv.feup.casadamusica.services;

import com.android.volley.Response;

import org.json.JSONObject;

public class ShowServices {

    public static void GetShows(Response.Listener<JSONObject> success, Response.ErrorListener fail){
        Api.Get("/shows", success, fail);
    }

    public static void GetPopularShows(Response.Listener<JSONObject> success, Response.ErrorListener fail){
        Api.Get("/shows/popular", success, fail);
    }
}
