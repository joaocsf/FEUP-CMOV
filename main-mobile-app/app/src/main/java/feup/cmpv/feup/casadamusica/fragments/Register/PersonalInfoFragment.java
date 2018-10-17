package feup.cmpv.feup.casadamusica.fragments.Register;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.structures.Registration;

public class PersonalInfoFragment extends Fragment {

    private EditText name;
    private EditText username;
    private EditText password;
    private EditText confirm_password;
    private EditText nif;

    private static Fragment instance;

    public static Fragment getInstance() {
        return new PersonalInfoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_info_register_fragment, container,false);

        name = view.findViewById(R.id.name_register);
        username = view.findViewById(R.id.username_register);
        password = view.findViewById(R.id.password_register);
        confirm_password = view.findViewById(R.id.confirm_password_register);
        nif = view.findViewById(R.id.nif_register);

        view.findViewById(R.id.next_button).setOnClickListener(v -> proceedRegistration());

        return view;
    }

    private void proceedRegistration(){

        if(!verifyFields()){
            return;
        }

        Registration registration = new Registration();

        registration.setName(name.getText().toString());
        registration.setUsername(username.getText().toString());
        registration.setPassword(password.getText().toString());
        registration.setNif(Integer.parseInt(nif.getText().toString()));

        CardInfoRegister cardInfoRegister = (CardInfoRegister)CardInfoRegister.getInstance(registration);

        getFragmentManager().beginTransaction().replace(R.id.fragment_host, cardInfoRegister).commit();
    }

    private boolean verifyFields(){
        boolean valid = true;

        if(name.getText().toString().isEmpty()){
            name.setError("Name can not be empty");
            valid = false;
        }

        if(username.getText().toString().isEmpty()){
            username.setError("Username can not be empty");
            valid = false;
        }

        if(password.getText().toString().isEmpty()){
            password.setError("Password can not be empty");
            valid = false;
        }

        if(confirm_password.getText().toString().isEmpty()){
            confirm_password.setError("Confirm password can not be empty");
            valid = false;
        }

        if(nif.getText().toString().isEmpty()){
            nif.setError("NIF can not be empty");
            valid = false;
        }

        if(!confirm_password.getText().toString().equals(password.getText().toString())){
            confirm_password.setError("It has to be equal to password");
            valid = false;
        }

        return valid;
    }
}
