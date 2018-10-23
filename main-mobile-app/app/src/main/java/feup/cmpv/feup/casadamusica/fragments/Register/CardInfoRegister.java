package feup.cmpv.feup.casadamusica.fragments.Register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyPairGenerator;
import java.util.Objects;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.services.Api;
import feup.cmpv.feup.casadamusica.services.CostumerServices;
import feup.cmpv.feup.casadamusica.structures.Card;
import feup.cmpv.feup.casadamusica.structures.Costumer;

public class CardInfoRegister extends Fragment {

    private static Fragment instance;

    private EditText card_number;
    private EditText card_validation_number;
    private RadioGroup card_type;

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

        view.findViewById(R.id.registration_button).setOnClickListener(v -> finalizeRegistration());

        return view;
    }

    private void finalizeRegistration(){

        if(!verifyFields()){
            return;
        }

        if(getArguments() != null) {

            Costumer costumer = (Costumer) getArguments().getSerializable("costumer");

            Card card = new Card();
            card.setValidity(Integer.parseInt(card_validation_number.getText().toString()));
            card.setNumber(card_number.getText().toString());
            switch (card_type.getCheckedRadioButtonId()) {
                case R.id.credit_type:
                    card.setType("Credit");
                    break;
                case R.id.debit_type:
                    card.setType("Debit");
                    break;
            }

            KeyPairGenerator kpg = KeyPairGenerator.getInstance()

            CostumerServices.Register(costumer,card,
                    response -> {
                        try {
                            Snackbar snackbar = null;
                            snackbar = Snackbar.make(Objects.requireNonNull(getView()), "User Created Successfuly!" + response.getString("msg"), Snackbar.LENGTH_LONG);
                            snackbar.show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        JSONObject obj = Api.getBodyFromError(error);

                        Snackbar snackbar = Snackbar.make(Objects.requireNonNull(getView()), "Error Adding User! " + obj, Snackbar.LENGTH_LONG);
                        snackbar.show();
                    });
        }
    }


    private boolean verifyFields(){
        boolean valid = true;

        if(card_number.getText().toString().isEmpty()){
            card_number.setError("Can not be empty");
            valid = false;
        }

        if(card_validation_number.getText().toString().isEmpty()){
            card_validation_number.setError("Can not be empty");
            valid = false;
        }

        return valid;
    }
}
