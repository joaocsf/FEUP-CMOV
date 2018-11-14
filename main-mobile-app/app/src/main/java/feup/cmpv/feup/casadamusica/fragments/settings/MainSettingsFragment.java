package feup.cmpv.feup.casadamusica.fragments.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.services.TerminalServices;
import feup.cmpv.feup.casadamusica.utils.Utils;
import feup.cmpv.feup.casadamusica.view.NFC.NFCSendActivity;

public class MainSettingsFragment extends Fragment {

    private String type;

    public static Fragment getInstance() {
        return new MainSettingsFragment();
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_main_fragment, container, false);
        InitializeView(view);
        return view;
    }

    private void InitializeView(View view) {
        Button btn = view.findViewById(R.id.settings_send_nfc);

        btn.setOnClickListener((v) -> {
            Intent intent = new Intent(this.getContext(), NFCSendActivity.class);
            intent.putExtra("message", "Message".getBytes());
            intent.putExtra("type", "order");
            startActivity(intent);
        });

        Button btn_read_qr = view.findViewById(R.id.settings_read_qr);

        btn_read_qr.setOnClickListener((bt) -> {
            type = "ticket";

            IntentIntegrator intentIntegrator =  IntentIntegrator.forSupportFragment(this);
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
            intentIntegrator.setPrompt("Read QR_code");
            intentIntegrator.setCameraId(0);
            intentIntegrator.setBeepEnabled(false);
            intentIntegrator.setOrientationLocked(false);
            intentIntegrator.initiateScan();
        });


        Button btn_read_qr_bar = view.findViewById(R.id.settings_read_qr_bar);

        btn_read_qr_bar.setOnClickListener((bt) -> {
            type = "bar";

            IntentIntegrator intentIntegrator =  IntentIntegrator.forSupportFragment(this);
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
            intentIntegrator.setPrompt("Read QR_code");
            intentIntegrator.setCameraId(0);
            intentIntegrator.setBeepEnabled(false);
            intentIntegrator.setOrientationLocked(false);
            intentIntegrator.initiateScan();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode + " " + resultCode + " " + data);
        if(result != null) {
            if(type.equals("ticket"))
                verifyDataTickets(result.getContents());
            else if(type.equals("bar"))
                verifyDataBar(result.getContents());
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void verifyDataBar(String contents) {
        try {
            JSONObject object = new JSONObject(contents);

            TerminalServices.Order(object,
                    this::parseOrders,
                    this::handleError);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void verifyDataTickets(String info){
        try {
            JSONObject object = new JSONObject(info);

            TerminalServices.ValidateTicket(object,
                    this::parseTickets,
                    this::handleError);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleError(VolleyError error) {

    }

    private void parseTickets(JSONObject obj) {
        try {
            Intent intent = Utils.OpenTicketActivity(obj);
            startActivity(intent);
        } catch (Exception e) {

        }
    }

    private void parseOrders(JSONObject obj){
        try {
            JSONObject order = null;
            order = obj.getJSONObject("order");

            Intent intent = Utils.OpenOrderActivity(order);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
