package feup.cmpv.feup.casadamusica.fragments.tickets;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.services.Api;
import feup.cmpv.feup.casadamusica.services.TicketServices;
import feup.cmpv.feup.casadamusica.structures.Show;
import feup.cmpv.feup.casadamusica.utils.Archive;

public class BuyTicketsDialogFragment extends DialogFragment implements View.OnClickListener {

    private Show show;
    private TextView number_of_tickets;
    private TextView total_price;

    public static DialogFragment getInstance(Show show) {
        DialogFragment fragment = new BuyTicketsDialogFragment();
        Bundle b = new Bundle();
        b.putSerializable("show", show);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.buy_ticket_fragment, container,false);

        show = (Show)Objects.requireNonNull(getArguments()).getSerializable("show");

        initializeView(view);

        Button buy_ticket = view.findViewById(R.id.buy_ticket);
        buy_ticket.setOnClickListener(this);

        Button cancel = view.findViewById(R.id.cancel_buy_ticket);
        cancel.setOnClickListener(this);

        Button plus_ticket = view.findViewById(R.id.plus_ticket);
        plus_ticket.setOnClickListener(this);

        Button minus_ticket = view.findViewById(R.id.minus_ticket);
        minus_ticket.setOnClickListener(this);

        return view;
    }

    private void initializeView(View view){
        TextView title = view.findViewById(R.id.show_title);
        title.setText(show.getName());

        TextView duration = view.findViewById(R.id.show_duration);
        duration.setText(new StringBuilder().append(show.getDuration()));

        TextView date = view.findViewById(R.id.show_date);
        date.setText(show.getDate());

        TextView price = view.findViewById(R.id.show_price);
        price.setText(new StringBuilder().append(show.getPrice()).append("€"));

        number_of_tickets = view.findViewById(R.id.number_of_tickets);
        number_of_tickets.setText("1");

        total_price = view.findViewById(R.id.total_price);
        total_price.setText(new StringBuilder().append(show.getPrice()).append("€"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buy_ticket:
                buy_ticket();
                break;
            case R.id.cancel_buy_ticket:
                cancel_buy_ticket();
                break;
            case R.id.plus_ticket:
                update_number_of_tickets(1);
                break;
            case R.id.minus_ticket:
                update_number_of_tickets(-1);
                break;
        }
    }

    private void update_number_of_tickets(int i) {
        int number = Integer.parseInt(number_of_tickets.getText().toString());
        number += i;

        if(number < 1)
            return;

        number_of_tickets.setText(new StringBuilder().append(number));
        update_total_value(number);
    }

    private void cancel_buy_ticket() {
        this.dismiss();
    }

    private void buy_ticket() {

        JSONObject tickets_obj = new JSONObject();
        try {
            tickets_obj.put("showId", show.getId());
            tickets_obj.put("numberOfTickets", Integer.parseInt(number_of_tickets.getText().toString()));
            tickets_obj.put("costumerUuid", Archive.getUuid());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TicketServices.BuyTicket(tickets_obj,
                response -> {
                    Toast.makeText( getContext(), "Success! ", Toast.LENGTH_SHORT).show();
                    this.dismiss();
                },
                error -> {
                    JSONObject obj = Api.getBodyFromError(error);
                    Toast.makeText(getContext(), "Error when buying tickets" + obj.toString(), Toast.LENGTH_SHORT).show();
                });
    }

    private void update_total_value(int number_tickets){
        float total = number_tickets * show.getPrice();

        total_price.setText(new StringBuilder().append(total).append("€"));
    }

    @Override
    public void onStart() {
        super.onStart();
        Objects.requireNonNull(getDialog().getWindow())
                .setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }
}
