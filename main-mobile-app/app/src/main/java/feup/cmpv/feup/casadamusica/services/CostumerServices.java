package feup.cmpv.feup.casadamusica.services;

import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Response;

import feup.cmpv.feup.casadamusica.structures.Costumer;
import feup.cmpv.feup.casadamusica.structures.Registration;

public class CostumerServices {

    public static void Register(Registration registration, Response.Listener<JSONObject> success, Response.ErrorListener fail){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", registration.getUsername());
            jsonObject.put("name", registration.getName());
            jsonObject.put("password", registration.getPassword());
            jsonObject.put("nif", registration.getNif());
            jsonObject.put("card_type", registration.getCardType());
            jsonObject.put("card_number", registration.getCardNumber());
            jsonObject.put("card_validation_number", registration.getCardValidationNumber());

            Api.Post("/registration", jsonObject, success, fail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
