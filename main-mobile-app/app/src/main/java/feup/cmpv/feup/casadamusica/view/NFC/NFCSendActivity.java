package feup.cmpv.feup.casadamusica.view.NFC;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import java.nio.charset.Charset;
import java.util.Arrays;

import feup.cmpv.feup.casadamusica.R;

public class NFCSendActivity extends AppCompatActivity implements NfcAdapter.OnNdefPushCompleteCallback {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        NfcAdapter nfcAdapter;
        String mimeType = "application/feup.cmpv.feup.casadamusica";
        mimeType = "application/nfc.feup.cmov.message.";
        byte[] message;

        setContentView(R.layout.nfc_send_activity);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null){
            Snackbar.make(findViewById(android.R.id.content), "NFC Not Available", Snackbar.LENGTH_LONG).show();
            finish();
        }

        if(nfcAdapter.isEnabled()){
            Snackbar.make(findViewById(android.R.id.content), "NFC Activated!", Snackbar.LENGTH_LONG).show();
        }else
            Snackbar.make(findViewById(android.R.id.content), "NFC NOT Activated!", Snackbar.LENGTH_LONG).show();

        Bundle extras = getIntent().getExtras();
        mimeType += extras.getString("type");
        message = extras.getByteArray("message");
        Log.d("MSG", Arrays.toString(message));
        Log.d("TYPE", mimeType);

        NdefMessage msg = new NdefMessage(new NdefRecord[]{createMimeRecord(mimeType, message)});
        nfcAdapter.setNdefPushMessage(msg, this);
        nfcAdapter.setOnNdefPushCompleteCallback(this, this);
    }

    public NdefRecord createMimeRecord(String mimeType, byte[] payload){
        byte[] mimeBytes = mimeType.getBytes(Charset.forName("ISO-8859-1"));
        return new NdefRecord(NdefRecord.TNF_MIME_MEDIA, mimeBytes, new byte[0], payload);

    }

    @Override
    public void onNdefPushComplete(NfcEvent event) {
        runOnUiThread(() -> {
            Snackbar.make(findViewById(android.R.id.content), "Done", Snackbar.LENGTH_LONG).show();
        });
    }
}
