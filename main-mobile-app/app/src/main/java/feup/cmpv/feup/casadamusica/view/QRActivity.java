package feup.cmpv.feup.casadamusica.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import feup.cmpv.feup.casadamusica.R;

public class QRActivity extends AppCompatActivity {

    private ImageView qrCodeImageview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.qr_activity);

        qrCodeImageview = findViewById(R.id.qrcode);

        generate(getMessage());
    }

    private String getMessage(){
        Bundle bundle = getIntent().getExtras();

        return new String(bundle.getByteArray("message"));
    }

    private void generate(String info) {
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        try {
            Bitmap bitmap = barcodeEncoder.encodeBitmap(info, BarcodeFormat.QR_CODE, 400,400);
            qrCodeImageview.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
