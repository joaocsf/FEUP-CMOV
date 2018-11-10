package feup.cmpv.feup.casadamusica.adapters.personal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.structures.Ticket;

public class TicketListAdapter extends ArrayAdapter<Ticket> {

    private Context contex;
    private List<Ticket> ticketList = new ArrayList<>();
    private Ticket currentTicket;

    public TicketListAdapter(@NonNull Context context, @NonNull List<Ticket> objects) {
        super(context, R.layout.ticket_list_item, objects);
        ticketList = objects;
        this.contex = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(contex).inflate(R.layout.ticket_list_item, parent, false);
        }

        currentTicket = ticketList.get(position);
        ImageView image = listItem.findViewById(R.id.ticket_list_item_image);
        TextView title = listItem.findViewById(R.id.ticket_list_item_title);
        TextView date = listItem.findViewById(R.id.ticket_list_item_date);
        TextView number_of_tickets = listItem.findViewById(R.id.ticket_list_item_number_of_tickets);

        return listItem;
    }

}