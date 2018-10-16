package feup.cmpv.feup.casadamusica.services;

import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Response;

import feup.cmpv.feup.casadamusica.structures.Costumer;

public class CostumerServices {

    public static void Register(Costumer costumer, Response.Listener<JSONObject> success, Response.ErrorListener fail){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", costumer.getUsername());
            jsonObject.put("name", costumer.getName());
            jsonObject.put("password", costumer.getPassword());
            jsonObject.put("nif", costumer.getNif());

            Api.Post("/costumer", jsonObject, success, fail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
