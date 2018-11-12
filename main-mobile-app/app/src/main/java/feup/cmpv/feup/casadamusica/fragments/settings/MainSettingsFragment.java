package feup.cmpv.feup.casadamusica.fragments.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.view.NFC.NFCSendActivity;

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
        Button btn = view.findViewById(R.id.settings_send_nfc);

        btn.setOnClickListener((v) -> {
            Intent intent = new Intent(this.getContext(), NFCSendActivity.class);
            intent.putExtra("message", "Message".getBytes());
            intent.putExtra("type", "order");
            startActivity(intent);
        });

        Button btn_read_qr = view.findViewById(R.id.settings_read_qr);

        btn_read_qr.setOnClickListener((bt) -> {
            IntentIntegrator intentIntegrator =  IntentIntegrator.forSupportFragment(this);
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
            if(result.getContents() == null) {
                Toast.makeText(this.getContext(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                System.out.println(result.getContents());
                Toast.makeText(this.getContext(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void parseResult(){

    }
}
