package feup.cmpv.feup.casadamusica.fragments.register;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

import javax.security.auth.x500.X500Principal;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.services.Api;
import feup.cmpv.feup.casadamusica.services.CostumerServices;
import feup.cmpv.feup.casadamusica.structures.Card;
import feup.cmpv.feup.casadamusica.structures.Costumer;
import feup.cmpv.feup.casadamusica.utils.Archive;
import feup.cmpv.feup.casadamusica.utils.SecurityConstants;
import feup.cmpv.feup.casadamusica.view.MainBody;

public class CardInfoRegister extends Fragment {

    private static final String TAG = "CardInfo";

    private EditText card_number;
    private EditText card_validation_number;
    private RadioGroup card_type;
    private Button registration_button;
    private ProgressBar loading;

    public static Fragment getInstance(Costumer costumer) {
        Fragment fragment = new CardInfoRegister();
        Bundle b = new Bundle();
        b.putSerializable("costumer", costumer);
        fragment.setArguments(b);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_info_register_fragment, container,false);

        card_number = view.findViewById(R.id.card_number_registration);
        card_validation_number = view.findViewById(R.id.card_validation_number_registration);
        card_type = view.findViewById(R.id.card_type);
        registration_button = view.findViewById(R.id.registration_button);
        loading = view.findViewById(R.id.loading);

        registration_button.setOnClickListener(v -> finalizeRegistration());

        return view;
    }

    private void finalizeRegistration(){
        showLoading(true);

        if(!verifyFields()){
            showLoading(false);
            return;
        }

        if(getArguments() != null) {

            Costumer costumer = (Costumer) getArguments().getSerializable("costumer");

            if(costumer != null) {


                Card card = new Card();
                card.setValidity(card_validation_number.getText().toString());
                card.setNumber(card_number.getText().toString());
                String cardTye = "Credit";

                if(card_type.getCheckedRadioButtonId() == R.id.debit_type){
                    cardTye = "Debit";
                }
                card.setType(cardTye);

                String pubKey = createKeyPair();

                costumer.setPublicKey(pubKey);

                CostumerServices.Register(costumer, card,
                        response -> {
                            try {
                                Toast.makeText( getContext(), "Successfully added! " + response.getString("msg"), Toast.LENGTH_SHORT).show();

                                Archive.setUuid(response.get("uuid").toString());

                                showLoading(false);

                                Intent intent = new Intent(getContext(), MainBody.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> {
                            this.deleteKey();
                            showLoading(false);
                            JSONObject obj = Api.getBodyFromError(error);
                            Toast.makeText( getContext(), "Error adding customer " + error.toString(), Toast.LENGTH_SHORT).show();
                        });
            }
        }
        showLoading(false);
    }


    private boolean verifyFields(){
        boolean valid = true;

        if(card_number.getText().toString().isEmpty()){
            card_number.setError("Can not be empty");
            valid = false;
        }else if(card_number.getText().toString().length() != 16){
            card_number.setError("Not valid, must have 16 digits");
            valid = false;
        }


        if(card_validation_number.getText().toString().isEmpty()){
            card_validation_number.setError("Can not be empty");
            valid = false;
        }else if(card_validation_number.getText().toString().length() > 4){
            card_validation_number.setError("Not valid, can not have more than 4 digits");
            valid = false;
        }

        return valid;
    }

    private String createKeyPair(){
        try {

            Calendar start = new GregorianCalendar();
            Calendar end = new GregorianCalendar();
            end.add(Calendar.YEAR, 20);

            KeyPairGenerator kgen = KeyPairGenerator.getInstance(SecurityConstants.TYPE_RSA, SecurityConstants.ANDROID_KEYSTORE);

            AlgorithmParameterSpec spec ;

            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
                spec = new KeyPairGeneratorSpec.Builder(Objects.requireNonNull(this.getContext()))
                        .setKeySize(SecurityConstants.KEY_SIZE)
                        .setAlias(SecurityConstants.KEY_NAME)
                        .setSubject(new X500Principal("CN=" + SecurityConstants.KEY_NAME))
                        .setSerialNumber(BigInteger.valueOf(1338))
                        .setStartDate(start.getTime())
                        .setEndDate(end.getTime())
                        .build();
            } else {
                spec = new KeyGenParameterSpec.Builder(SecurityConstants.KEY_NAME, KeyProperties.PURPOSE_SIGN)
                        .setCertificateSubject(new X500Principal("CN=" + SecurityConstants.KEY_NAME))
                        .setDigests(KeyProperties.DIGEST_SHA256)
                        .setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1)
                        .setCertificateSerialNumber(BigInteger.valueOf(1337))
                        .setCertificateNotBefore(start.getTime())
                        .setCertificateNotAfter(end.getTime())
                        .build();
            }


            kgen.initialize(spec);

            KeyPair kp = kgen.generateKeyPair();

            byte[] publicKeyEnc = kp.getPublic().getEncoded();

            return Base64.encodeToString(publicKeyEnc, Base64.DEFAULT);

        }
        catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
        }

        return null;
    }

    private void deleteKey(){
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance(SecurityConstants.ANDROID_KEYSTORE);
            keyStore.load(null);

            keyStore.deleteEntry(SecurityConstants.KEY_NAME);

        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            e.printStackTrace();
        }
    }

    private void showLoading(Boolean show){
        if(show){
            registration_button.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
        }else{
            registration_button.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
        }
    }

}
