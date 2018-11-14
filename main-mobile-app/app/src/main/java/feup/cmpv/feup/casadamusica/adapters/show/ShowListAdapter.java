package feup.cmpv.feup.casadamusica.adapters.show;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.structures.Show;

import static feup.cmpv.feup.casadamusica.utils.Utils.df2;

public class ShowListAdapter extends ArrayAdapter<Show>{

    private Context contex;
    private List<Show> showList = new ArrayList<>();
    private Show currentShow;
    private int layoutRes;

    public ShowListAdapter(@NonNull Context context, @NonNull List<Show> objects, @LayoutRes int layout) {
        super(context, layout, objects);
        showList = objects;
        this.contex = context;
        layoutRes = layout;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(contex).inflate(layoutRes, parent, false);
        }

        currentShow = showList.get(position);
        ImageView image = listItem.findViewById(R.id.show_list_item_image);
        TextView title = listItem.findViewById(R.id.show_list_item_title);
        TextView date = listItem.findViewById(R.id.show_list_item_date);
        TextView price = listItem.findViewById(R.id.show_list_item_price);
        TextView atendees = listItem.findViewById(R.id.show_list_item_atendees);
        CheckBox toggleButton = listItem.findViewById(R.id.show_list_item_toggle);
        Show show = showList.get(position);

        if(toggleButton != null){
            toggleButton.setOnCheckedChangeListener(
                    (v, isChecked) -> {
                        show.setSelected(isChecked);
                    }
            );
        }

        if(atendees != null)
            atendees.setText(new StringBuilder().append(currentShow.getAtendees()));
        if(title != null)
            title.setText(currentShow.getName());
        if(date != null)
            date.setText(currentShow.getDate());

        if(price != null){
            String show_price = df2.format(currentShow.getPrice());
            price.setText(new StringBuilder().append(show_price).append("€"));
        }
        return listItem;
    }
}
