package feup.cmpv.feup.casadamusica.fragments.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.view.NFC.NFCSendActivity;

public class MainSettingsFragment extends Fragment {

    public static Fragment getInstance() {
        Fragment fragment = new MainSettingsFragment();
        return fragment;
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
    }
}
