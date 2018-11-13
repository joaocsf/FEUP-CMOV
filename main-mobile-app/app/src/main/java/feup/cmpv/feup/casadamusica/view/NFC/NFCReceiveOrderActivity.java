package feup.cmpv.feup.casadamusica.view.NFC;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.IntentCompat;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.fragments.bar.BarPurchaseConfirmFragment;
import feup.cmpv.feup.casadamusica.services.TerminalServices;
import feup.cmpv.feup.casadamusica.structures.Product;
import feup.cmpv.feup.casadamusica.utils.Utils;

public class NFCReceiveOrderActivity extends NFCReceiveActivity{
    TextView tv;
    @Override
    protected void onDataReceived(String data) {
        try {
            JSONObject object = new JSONObject(data);

            TerminalServices.Order(object,
                    this::parseOrder,
                    this::handleError);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleError(VolleyError error){
        tv.setText("FAILURE");
    }

    private void parseOrder(JSONObject response) {
        try {

            JSONObject order = response.getJSONObject("order");
            Intent intent = Utils.OpenOrderActivity(order);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_receive_activity);
        tv = findViewById(R.id.nfc_validating_order);
    }
}
