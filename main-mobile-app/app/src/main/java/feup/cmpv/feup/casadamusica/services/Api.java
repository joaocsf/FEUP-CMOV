package feup.cmpv.feup.casadamusica.services;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import feup.cmpv.feup.casadamusica.application.ApplicationContextRetriever;
import feup.cmpv.feup.casadamusica.utils.Archive;
import feup.cmpv.feup.casadamusica.utils.Config;

public class Api {

    private static RequestQueue queue;
    private static final String host = Config.HOST;


    private static RequestQueue getQueue(){
        if(queue == null){
            queue = Volley.newRequestQueue(ApplicationContextRetriever.getContext());
        }
        return queue;
    }

    private static <T> void addRequest(Request<T> request){
        Log.d("API Request", "Request Added " + request.getUrl());
        getQueue().add(request);
    }

    public static JSONObject getBodyFromError(VolleyError error){
        JSONObject object;

        try {
            object = new JSONObject(new String(error.networkResponse.data));
        } catch (Exception e) {
            object = new JSONObject();
        }

        return object;
    }

    public static void Get(String path, Response.Listener<JSONObject> success, Response.ErrorListener fail){
        String url = host + path;

        JSONObject cached = Archive.LoadJSON(path);
        Log.d("Loaded", (cached != null) + " - " + path);

        if(cached!=null)
            success.onResponse(cached);

        Response.Listener<JSONObject> success2 = (object)-> {
            Log.d("REQUEST","RECEIVED");
            success.onResponse(object);
            Archive.SaveJSON(path, object);
        };

        JsonObjectRequest request = new JsonObjectRequestHeaders(Request.Method.GET, url, null, success2, fail);
        addRequest(request);

    }

    public static void Delete(String path, Response.Listener<JSONObject> success, Response.ErrorListener fail){
        String url = host + path;

        JsonObjectRequest request = new JsonObjectRequestHeaders(Request.Method.DELETE, url, null, success, fail);

        addRequest(request);
    }

    public  static void Post(String path, JSONObject jsonRequest, Response.Listener<JSONObject> success, Response.ErrorListener fail){
        String url = host + path;

        JsonObjectRequest request = new JsonObjectRequestHeaders(Request.Method.POST, url, jsonRequest, success, fail);

        addRequest(request);
    }

    public  static void Put(String path, JSONObject jsonRequest, Response.Listener<JSONObject> success, Response.ErrorListener fail){
        String url = host + path;

        JsonObjectRequest request = new JsonObjectRequestHeaders(Request.Method.PUT, url, jsonRequest, success, fail);

        addRequest(request);
    }


}
