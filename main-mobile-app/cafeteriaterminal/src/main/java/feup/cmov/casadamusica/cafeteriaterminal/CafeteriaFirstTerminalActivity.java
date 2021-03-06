package feup.cmov.casadamusica.cafeteriaterminal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.android.volley.VolleyError;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import feup.cmpv.feup.casadamusica.services.TerminalServices;
import feup.cmpv.feup.casadamusica.utils.Archive;
import feup.cmpv.feup.casadamusica.utils.Utils;
import feup.cmpv.feup.casadamusica.view.TerminalLoginActivity;

public class CafeteriaFirstTerminalActivity extends AppCompatActivity {
    boolean killed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ON CREATE" ,"CREATE");

    }

    @Override
    protected void onResume() {
        if(killed){
            super.onResume();
            return;
        }
        Log.d("RESUME" ,"RESUME");
        Intent intent;
        if(Archive.getToken() == null){
            intent = new Intent(this, TerminalLoginActivity.class);
            startActivityForResult(intent, 5);
        }else{
            openTerminal();
        }
        super.onResume();
    }

    private void openTerminal(){
        Intent intent;
        intent = new Intent(this, CafeteriaTerminalActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("Response:", requestCode + " " + resultCode);
        if(requestCode == 5 && resultCode == 1){
            openTerminal();
            return;
        }
        killed=true;
        finish();
    }
}
