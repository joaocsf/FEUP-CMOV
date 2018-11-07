package feup.cmpv.feup.casadamusica.adapters.bar;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DebugUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.structures.Product;
import feup.cmpv.feup.casadamusica.structures.Show;

public class ProductListAdapter extends ArrayAdapter<Product> {

    private Context context;
    private List<Product> productList = new ArrayList<>();
    private int layoutRes;

    public ProductListAdapter(@NonNull Context context, @NonNull List<Product> objects,@LayoutRes int layoutRes) {
        super(context, R.layout.show_list_item, objects);
        productList = objects;
        this.context = context;
        this.layoutRes = layoutRes;
    }

    public void Reset(){
        for(Product p : productList){
            p.Reset();
        }
    }

    void IncreaseQuantity(TextView tv, Product product, boolean up) {
        if (up)
            product.Add();
        else
            product.Remove();
        ((Activity)context).runOnUiThread(()->{
            tv.setText(product.getQuantity() + "");

            Log.d("Test", "123");
        });
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null){
            listItem = LayoutInflater.from(context).inflate(layoutRes, parent, false);
        }

        Product currentProduct = productList.get(position);
        ImageView image = listItem.findViewById(R.id.show_list_item_image);
        TextView title = listItem.findViewById(R.id.product_list_item_title);
        TextView price = listItem.findViewById(R.id.product_list_item_price);
        TextView quantity = listItem.findViewById(R.id.product_list_item_quantity);

        Button addbtn = listItem.findViewById(R.id.product_list_item_add);
        Button removebtn = listItem.findViewById(R.id.product_list_item_remove);

        if(addbtn!= null)
            addbtn.setOnClickListener((click) -> IncreaseQuantity(quantity, currentProduct, true));
        if(removebtn!=null)
            removebtn.setOnClickListener((click) -> IncreaseQuantity(quantity, currentProduct, false));

        if(title!=null)
            title.setText(currentProduct.getName());

        if(price!= null)
            price.setText(currentProduct.getPrice() + "€");

        if(quantity != null)
            quantity.setText(currentProduct.getQuantity() + "");

        return listItem;

    }
}
