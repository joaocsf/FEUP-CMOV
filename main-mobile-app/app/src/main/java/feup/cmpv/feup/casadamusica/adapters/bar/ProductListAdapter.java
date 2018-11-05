package feup.cmpv.feup.casadamusica.adapters.bar;

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
import feup.cmpv.feup.casadamusica.structures.Product;
import feup.cmpv.feup.casadamusica.structures.Show;

public class ProductListAdapter extends ArrayAdapter<Product> {

    private Context contex;
    private List<Product> productList = new ArrayList<>();

    public ProductListAdapter(@NonNull Context context, @NonNull List<Product> objects) {
        super(context, R.layout.show_list_item, objects);
        productList = objects;
        this.contex = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(contex).inflate(R.layout.bar_list_item, parent, false);
        }

        Product currentProduct = productList.get(position);
        ImageView image = listItem.findViewById(R.id.show_list_item_image);
        TextView title = listItem.findViewById(R.id.product_list_item_title);
        TextView price = listItem.findViewById(R.id.product_list_item_price);

        title.setText(currentProduct.name);
        price.setText(currentProduct.price + "€");

        return listItem;

    }
}
