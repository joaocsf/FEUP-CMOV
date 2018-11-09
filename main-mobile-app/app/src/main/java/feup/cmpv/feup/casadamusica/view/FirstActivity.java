package feup.cmpv.feup.casadamusica.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyStore;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.services.Api;
import feup.cmpv.feup.casadamusica.services.CostumerServices;
import feup.cmpv.feup.casadamusica.structures.Card;
import feup.cmpv.feup.casadamusica.structures.Costumer;
import feup.cmpv.feup.casadamusica.utils.Archive;
import feup.cmpv.feup.casadamusica.utils.SecurityConstants;

public class FirstActivity extends AppCompatActivity {

    CoordinatorLayout coordinatorLayout;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent;
        if(Archive.hasKey()){
            Fake_Login();
            intent = new Intent(this, MainBody.class);
        }else{
            intent = new Intent(this, UserRegister.class);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(intent);
    }

    private void Fake_Login(){
        String publicKeyString = Archive.publickKey64();
        Costumer costumer = new Costumer();
        costumer.setUsername("username");
        costumer.setPassword("password");
        costumer.setPublicKey(publicKeyString);

        CostumerServices.Login(costumer, (obj) -> {
            try {
                Archive.setUuid(obj.getString("uuid"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, (error) -> { Log.d("Login", "Error Logging In!");});


    }
}
