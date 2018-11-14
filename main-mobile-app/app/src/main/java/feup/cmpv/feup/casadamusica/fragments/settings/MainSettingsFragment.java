package feup.cmpv.feup.casadamusica.fragments.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Objects;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.utils.Archive;
import feup.cmpv.feup.casadamusica.view.LoginActivity;

public class MainSettingsFragment extends Fragment {

    public static Fragment getInstance() {
        return new MainSettingsFragment();
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_main_fragment, container, false);
        InitializeView(view);
        return view;
    }

    private void InitializeView(View view) {
        Button btn_logout = view.findViewById(R.id.settings_logout);
        btn_logout.setOnClickListener(this::logout);
    }

    private void logout(View view) {
        Intent intent = new Intent(this.getActivity(), LoginActivity.class);

        Objects.requireNonNull(getActivity()).finish();
        Archive.deleteKey();
        Archive.setUuid(null);

        startActivity(intent);
    }
}
