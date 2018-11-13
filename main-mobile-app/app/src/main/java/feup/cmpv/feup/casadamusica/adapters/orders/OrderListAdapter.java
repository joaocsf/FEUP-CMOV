package feup.cmpv.feup.casadamusica.adapters.orders;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.structures.Order;
import feup.cmpv.feup.casadamusica.structures.Product;

import static feup.cmpv.feup.casadamusica.utils.Utils.df2;
import static feup.cmpv.feup.casadamusica.utils.Utils.formatDate;

public class OrderListAdapter extends ArrayAdapter<Order> {

    private Context context;
    private List<Order> orderList = new ArrayList<>();

    public OrderListAdapter(@NonNull Context context, @NonNull List<Order> objects) {
        super(context, R.layout.order_list_item, objects);
        orderList = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.order_list_item, parent, false);
        }

        Order currentOrder = orderList.get(position);
        currentOrder.parseEverything();

        TextView orderNumber = listItem.findViewById(R.id.order_list_item_id);
        TextView orderDate = listItem.findViewById(R.id.order_list_item_date);
        TextView orderValue = listItem.findViewById(R.id.order_list_item_value);

        orderValue.setText(df2.format(currentOrder.getTotal()) + "€");
        orderDate.setText(formatDate(currentOrder.getDate()));
        orderNumber.setText("#" + currentOrder.getId());

        return listItem;
    }
}
