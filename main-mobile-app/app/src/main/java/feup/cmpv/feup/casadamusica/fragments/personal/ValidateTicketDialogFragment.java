package feup.cmpv.feup.casadamusica.fragments.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.NumberPicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Objects;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.structures.ShowTickets;
import feup.cmpv.feup.casadamusica.utils.Archive;
import feup.cmpv.feup.casadamusica.utils.Utils;

public class ValidateTicketDialogFragment extends DialogFragment implements View.OnClickListener {
    private ShowTickets showTickets;
    private NumberPicker np;
    private Button btnGenerate;

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
        View view = inflater.inflate(R.layout.validate_tickets_dialog_fragment, container,false);

        if (getArguments() != null) {
            showTickets = (ShowTickets)getArguments().getSerializable("showTickets");
        }

        np = view.findViewById(R.id.number_of_tickets);
        np.setMinValue(1);

        int max_tickets = Math.min(4, showTickets.getTickets().size());

        np.setMaxValue(max_tickets);

        btnGenerate = view.findViewById(R.id.btn_generate);

        btnGenerate.setOnClickListener(this);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        Objects.requireNonNull(getDialog().getWindow())
                .setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onClick(View view) {

        Intent intent = Utils.initializeTransfer(generateMessage(showTickets, np.getValue()), "ticket");

        startActivity(intent);
    }


    private static String generateMessage(ShowTickets showTickets, int numberOfTickets){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        OutputStreamWriter osw;
        JSONObject object = null;

        try {
            osw = new OutputStreamWriter(byteArrayOutputStream, "ASCII");
            osw.write(Archive.getUuid());
            osw.write("_");
            osw.write(numberOfTickets);
            osw.write("_");

            for(int i = 0; i < numberOfTickets; i++){
                osw.write(showTickets.getTickets().get(i).getUuid());
                osw.write("_");
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
