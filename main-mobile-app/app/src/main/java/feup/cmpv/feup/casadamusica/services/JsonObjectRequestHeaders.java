package feup.cmpv.feup.casadamusica.services;

import android.support.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import feup.cmpv.feup.casadamusica.utils.Archive;

public class JsonObjectRequestHeaders extends JsonObjectRequest {
    public JsonObjectRequestHeaders(int method, String url, @Nullable JSONObject jsonRequest, Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();

        if(Archive.getUuid() != null){
            params.put("uuid", Archive.getUuid());
            params.put("verification", Archive.Sign(Archive.getUuid()));
        }
        if(Archive.getToken() != null){
            params.put("auth", Archive.getToken());
        }
        return params;
    }
}
