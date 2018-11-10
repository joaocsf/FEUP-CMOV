package feup.cmpv.feup.casadamusica.fragments.bar;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.adapters.bar.ProductListAdapter;
import feup.cmpv.feup.casadamusica.structures.Product;

public class BarPurchaseConfirmFragment extends DialogFragment {

    private List<Product> productList;

    public static BarPurchaseConfirmFragment getInstance(ArrayList<Product> products) {
        BarPurchaseConfirmFragment fragment = new BarPurchaseConfirmFragment();
        Bundle b = new Bundle();
        b.putSerializable("products", products);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bar_purchase_confirm_fragment, container,false);
        InitializeView(view);
        return view;
    }

    public void InitializeView(View view){
        ListView listView = view.findViewById(R.id.purchase_product_list);
        TextView totalTV = view.findViewById(R.id.purchase_total);
        ArrayList<Product> products = (ArrayList<Product>)getArguments().getSerializable("products");
        ProductListAdapter adapter = new ProductListAdapter(view.getContext(), products , R.layout.purchase_product_list_item, false);
        float total = 0;
        for(Product p : products){
            total += p.getPrice();
        }
        totalTV.setText(total + "€");

        listView.setAdapter(adapter);
        listView.computeScroll();

    }
}
