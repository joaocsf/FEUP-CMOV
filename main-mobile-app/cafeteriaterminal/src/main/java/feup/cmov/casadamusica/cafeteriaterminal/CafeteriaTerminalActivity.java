package feup.cmov.casadamusica.cafeteriaterminal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.android.volley.VolleyError;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import feup.cmpv.feup.casadamusica.services.TerminalServices;
import feup.cmpv.feup.casadamusica.utils.Utils;

public class CafeteriaTerminalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafeteria_terminal);

        Button btn = findViewById(R.id.ticket_terminal_read_qr);

        btn.setOnClickListener((v)-> {
            IntentIntegrator intentIntegrator =  new IntentIntegrator(this);
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
            verifyOrders(result.getContents());
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void verifyOrders(String info){
        try {
            JSONObject object = new JSONObject(info);

            TerminalServices.Order(object,
                    this::parseOrders,
                    this::handleError);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleError(VolleyError error) {

    }

    private void parseOrders(JSONObject obj) {
        try {
            JSONObject order = obj.getJSONObject("order");
            Intent intent = Utils.OpenOrderActivity(order);
            startActivity(intent);
        } catch (Exception e) {

        }
    }
}
