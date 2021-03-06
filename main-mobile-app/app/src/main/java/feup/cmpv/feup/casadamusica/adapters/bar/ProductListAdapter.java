package feup.cmpv.feup.casadamusica.adapters.bar;

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
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.structures.Product;

import static feup.cmpv.feup.casadamusica.utils.Utils.df2;

public class ProductListAdapter extends ArrayAdapter<Product> {

    private Context context;
    private List<Product> productList = new ArrayList<>();
    private int layoutRes;
    private boolean addDummy;

    public ProductListAdapter(@NonNull Context context, @NonNull List<Product> objects,@LayoutRes int layoutRes, boolean addDummy) {
        super(context, R.layout.show_list_item, objects);
        productList = objects;
        this.context = context;
        this.layoutRes = layoutRes;
        this.addDummy = addDummy;
    }

    public void Reset(){
        for(Product p : productList){
            p.Reset();
        }
    }

    private void IncreaseQuantity(TextView tv, Product product, boolean up) {
        if (up)
            product.Add();
        else
            product.Remove();
        ((Activity)context).runOnUiThread(()->{
            tv.setText(String.valueOf(product.getQuantity()));
        });
    }

    @Override
    public int getViewTypeCount() {
        return addDummy? 2 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == productList.size() -1 && addDummy? 1 : 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        Product currentProduct = productList.get(position);
        Log.d("Adapter", "Position:" + position + "- " + currentProduct.getName());

        if(listItem == null){

            if(addDummy && position == productList.size() - 1)
                return LayoutInflater.from(context).inflate(R.layout.dummy_list_item, parent, false);
            else
                listItem = LayoutInflater.from(context).inflate(layoutRes, parent, false);
        }

        ImageView image = listItem.findViewById(R.id.product_list_item_image);
        TextView title = listItem.findViewById(R.id.product_list_item_title);
        TextView price = listItem.findViewById(R.id.product_list_item_price);
        TextView quantity = listItem.findViewById(R.id.product_list_item_quantity);

        ImageButton addbtn = listItem.findViewById(R.id.product_list_item_add);
        ImageButton removebtn = listItem.findViewById(R.id.product_list_item_remove);

        if(image != null){
            Ion.with(image)
                    .error(R.drawable.ic_launcher_background)
                    .placeholder(R.drawable.ic_launcher_background).load(currentProduct.getImage());
            Log.d("Cheesy Fries", currentProduct.getImage());
        }

        if(addbtn!= null)
            addbtn.setOnClickListener((click) -> IncreaseQuantity(quantity, currentProduct, true));
        if(removebtn!=null)
            removebtn.setOnClickListener((click) -> IncreaseQuantity(quantity, currentProduct, false));

        if(title!=null)
            title.setText(currentProduct.getName());

        if(price!= null)
            price.setText(new StringBuilder().append(df2.format(currentProduct.getPrice())).append("€"));

        if(quantity != null)
            quantity.setText(String.valueOf(currentProduct.getQuantity()));

        return listItem;

    }
}
