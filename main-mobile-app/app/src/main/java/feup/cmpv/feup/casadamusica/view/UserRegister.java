package feup.cmpv.feup.casadamusica.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Window;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.fragments.Register.PersonalInfoFragment;
import feup.cmpv.feup.casadamusica.services.CostumerServices;
import feup.cmpv.feup.casadamusica.structures.Costumer;
import feup.cmpv.feup.casadamusica.utils.Archive;
import feup.cmpv.feup.casadamusica.utils.SecurityConstants;

public class UserRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.user_register);

        try {
            KeyStore keyStore = KeyStore.getInstance(SecurityConstants.ANDROID_KEYSTORE);
            keyStore.load(null);

            Boolean registered = keyStore.containsAlias(SecurityConstants.KEY_NAME);


            if(registered){
                Intent intent = new Intent(this, MainBody.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                KeyStore.PrivateKeyEntry entry = (KeyStore.PrivateKeyEntry)keyStore.getEntry(SecurityConstants.KEY_NAME, null);
                Certificate certificate = keyStore.getCertificate(SecurityConstants.KEY_NAME);
                PublicKey pk = certificate.getPublicKey();
                String publicKeyString = Base64.encodeToString(pk.getEncoded(), Base64.DEFAULT);
                Log.d("PUBLIC_KEY", publicKeyString);

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

                startActivity(intent);
            }else{
                PersonalInfoFragment personalInfoFragment = (PersonalInfoFragment) PersonalInfoFragment.getInstance();

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.fragment_host, personalInfoFragment);
                ft.commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
