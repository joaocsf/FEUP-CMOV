package feup.cmpv.feup.casadamusica.adapters.show;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.structures.Show;

public class ShowListAdapter extends ArrayAdapter<Show> {

    private Context contex;
    private List<Show> showList = new ArrayList<>();

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

        Show currentShow = showList.get(position);
        ImageView image = listItem.findViewById(R.id.show_list_item_image);
        TextView title = listItem.findViewById(R.id.show_list_item_title);
        TextView date = listItem.findViewById(R.id.show_list_item_date);
        TextView price = listItem.findViewById(R.id.show_list_item_price);

        title.setText(currentShow.name);
        date.setText(currentShow.date);
        price.setText(currentShow.price + "");

        return listItem;

    }
}
