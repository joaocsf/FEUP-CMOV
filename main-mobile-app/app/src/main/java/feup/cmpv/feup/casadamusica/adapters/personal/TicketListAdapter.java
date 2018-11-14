package feup.cmpv.feup.casadamusica.adapters.personal;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.List;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.structures.ShowTickets;

public class TicketListAdapter extends ArrayAdapter<ShowTickets> {

    private Context contex;
    private List<ShowTickets> ticketList;
    private ShowTickets currentShowTickets;
    private int layoutRes;

    public TicketListAdapter(@NonNull Context context, @NonNull List<ShowTickets> objects, @LayoutRes int layoutRes) {
        super(context, R.layout.ticket_list_item, objects);
        this.ticketList = objects;
        this.contex = context;
        this.layoutRes = layoutRes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null){
            listItem = LayoutInflater.from(contex).inflate(this.layoutRes, parent, false);
        }

        currentShowTickets = ticketList.get(position);

        ImageView image = listItem.findViewById(R.id.ticket_list_item_image);
        TextView title = listItem.findViewById(R.id.ticket_list_item_title);
        TextView date = listItem.findViewById(R.id.ticket_list_item_date);
        TextView seats = listItem.findViewById(R.id.ticket_list_item_seats);
        TextView seatNumber = listItem.findViewById(R.id.ticket_list_item_seats_number);

        if(image != null){
            Ion.with(image)
                    .error(R.drawable.ic_launcher_background)
                    .placeholder(R.drawable.ic_launcher_background).load(currentShowTickets.getShow().getImage());
        }

        if(title != null)
            title.setText(currentShowTickets.getShow().getName());

        if(date != null)
            date.setText(currentShowTickets.getShow().getDate());

        if(seats != null)
            seats.setText(getTicketSeats());

        if(seatNumber != null)
            seatNumber.setText("x"+currentShowTickets.getTickets().size());


        return listItem;
    }

    private String getTicketSeats() {

        StringBuilder tickets = new StringBuilder();

        for(int i=0; i < currentShowTickets.getTickets().size(); i++){
            tickets.append(currentShowTickets.getTickets().get(i).getSeat());

            if(i != currentShowTickets.getTickets().size() - 1)
                tickets.append(", ");
        }

        return tickets.toString();
    }

}