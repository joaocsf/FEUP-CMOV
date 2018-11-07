package feup.cmpv.feup.casadamusica.adapters.show;

import android.app.Activity;
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
import feup.cmpv.feup.casadamusica.fragments.tickets.BuyTicketsDialogFragment;
import feup.cmpv.feup.casadamusica.structures.Show;
import feup.cmpv.feup.casadamusica.view.MainBody;

public class ShowListAdapter extends ArrayAdapter<Show> implements View.OnClickListener {

    private Context contex;
    private List<Show> showList = new ArrayList<>();
    private Show currentShow;

    public ShowListAdapter(@NonNull Context context, @NonNull List<Show> objects) {
        super(context, R.layout.show_list_item, objects);
        showList = objects;
        this.contex = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(contex).inflate(R.layout.show_list_item, parent, false);
        }

        currentShow = showList.get(position);
        ImageView image = listItem.findViewById(R.id.show_list_item_image);
        TextView title = listItem.findViewById(R.id.show_list_item_title);
        TextView date = listItem.findViewById(R.id.show_list_item_date);
        TextView price = listItem.findViewById(R.id.show_list_item_price);
        TextView atendees = listItem.findViewById(R.id.show_list_item_atendees);

        atendees.setText(new StringBuilder().append(currentShow.getAtendees()));
        title.setText(currentShow.getName());
        date.setText(currentShow.getDate());
        price.setText(new StringBuilder().append(currentShow.getPrice()).append("€"));

        listItem.setOnClickListener(this);

        return listItem;

    }

    @Override
    public void onClick(View view) {
        BuyTicketsDialogFragment buyTicketsDialogFragment = (BuyTicketsDialogFragment) BuyTicketsDialogFragment.getInstance(currentShow);
        buyTicketsDialogFragment.show(((MainBody)getContext()).getSupportFragmentManager(), "Buy Ticket");
    }
}
