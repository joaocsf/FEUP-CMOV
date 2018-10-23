package feup.cmpv.feup.casadamusica.services;

import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Response;

import feup.cmpv.feup.casadamusica.structures.Card;
import feup.cmpv.feup.casadamusica.structures.Costumer;

public class CostumerServices {

    public static void Register(Costumer costumer, Card card, Response.Listener<JSONObject> success, Response.ErrorListener fail){
        try {
            JSONObject user = new JSONObject();

            user.put("username", costumer.getUsername());
            user.put("name", costumer.getName());
            user.put("password", costumer.getPassword());
            user.put("nif", costumer.getNif());
            user.put("publicKey", costumer.getPublicKey());

            JSONObject cd = new JSONObject();
            cd.put("number", card.getNumber());
            cd.put("validity", card.getValidity());
            cd.put("type", card.getType());

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user",user);
            jsonObject.put("card",card);

            Api.Post("/costumer/create", jsonObject, success, fail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
