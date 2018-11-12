package feup.cmpv.feup.casadamusica.fragments.personal;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Objects;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.structures.ShowTickets;
import feup.cmpv.feup.casadamusica.utils.Archive;

public class ValidateTicketDialogFragment extends DialogFragment {
    private ImageView qrCodeImageview;
    private ShowTickets showTickets;

    public static DialogFragment getInstance(ShowTickets showTickets){
        DialogFragment fragment = new ValidateTicketDialogFragment();
        Bundle b = new Bundle();
        b.putSerializable("showTickets", showTickets);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.validate_ticket_dialog_fragment, container,false);

        qrCodeImageview = view.findViewById(R.id.qrcode);

        if (getArguments() != null) {
            showTickets = (ShowTickets)getArguments().getSerializable("showTickets");
        }

        // change to the number of tickets that the person wants... max 4
        this.generate(this.generateMessage(showTickets, 1));

        return view;
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

    private String generateMessage(ShowTickets showTickets, int numberOfTickets){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        OutputStreamWriter osw;
        JSONObject object = null;

        try {
            osw = new OutputStreamWriter(byteArrayOutputStream, "ASCII");
            osw.write(Archive.getUuid());
            osw.write("|");
            osw.write(numberOfTickets);
            osw.write("|");

            for(int i =0; i < numberOfTickets; i++){
                osw.write(showTickets.getTickets().get(i).getUuid());
                osw.write("|");
            }

            osw.write(showTickets.getShow().getId());

            osw.flush();
            byteArrayOutputStream.flush();

            String ticketsString = byteArrayOutputStream.toString();
            String ticketsSign = Archive.Sign(ticketsString);

            object = new JSONObject();
            object.put("tickets", ticketsString);
            object.put("validation", ticketsSign);

            return object.toString();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return Objects.requireNonNull(object).toString();
    }
}
