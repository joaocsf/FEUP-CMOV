package feup.cmpv.feup.casadamusica.fragments.Register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.structures.Registration;

public class CardInfoRegister extends Fragment {

    private static Fragment instance;

    public static Fragment getInstance(Registration registration) {
        Fragment fragment = new CardInfoRegister();
        Bundle b = new Bundle();
        b.putSerializable("registration", registration);
        fragment.setArguments(b);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_info_register_fragment, container,false);
        return view;
    }
}
