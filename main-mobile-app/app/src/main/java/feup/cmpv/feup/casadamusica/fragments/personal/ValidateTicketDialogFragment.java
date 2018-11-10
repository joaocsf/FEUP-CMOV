package feup.cmpv.feup.casadamusica.fragments.personal;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import java.util.Objects;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.structures.ShowTickets;
import feup.cmpv.feup.casadamusica.utils.QRCodeUtils;

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
        this.generate(QRCodeUtils.generateMessage(showTickets, 1));

        return view;
    }

    private void generate(String info) {
        new Thread(new convertToQR(info)).start();
    }

    class convertToQR implements Runnable {
        String content;

        convertToQR(String info) {
            content = info;
        }

        @Override
        public void run() {
            final Bitmap bitmap;
            final String errorMsg;

            try {
                bitmap = QRCodeUtils.encodeAsBitmap(content, getContext());

                Objects.requireNonNull(getActivity()).runOnUiThread(() -> qrCodeImageview.setImageBitmap(bitmap));
            }
            catch (WriterException e) {
                errorMsg = e.getMessage();
                Log.d("QRCODE", errorMsg);

                Objects.requireNonNull(getActivity()).runOnUiThread(() -> Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show());
            }
        }
    }
}
