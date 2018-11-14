package feup.cmpv.feup.casadamusica.view.NFC;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.fragments.bar.BarPurchaseConfirmFragment;
import feup.cmpv.feup.casadamusica.services.TerminalServices;
import feup.cmpv.feup.casadamusica.structures.Product;
import feup.cmpv.feup.casadamusica.utils.Utils;

public class NFCReceiveTicketActivationActivity extends NFCReceiveActivity{
    TextView tv;
    @Override
    protected void onDataReceived(String data) {
        Log.d("DATA", data);
        try {
            JSONObject object = new JSONObject(data);
            TerminalServices.ValidateTicket(object,
            (obj)-> {
                try {

                    Intent intent = Utils.OpenTicketActivity(obj);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            },
            (error)->{
                tv.setText("FAILURE");
            });
        } catch (JSONException e) {
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
