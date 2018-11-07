package feup.cmpv.feup.casadamusica.fragments.bar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.adapters.bar.ProductListAdapter;
import feup.cmpv.feup.casadamusica.adapters.show.ShowListAdapter;
import feup.cmpv.feup.casadamusica.listeners.IProductListener;
import feup.cmpv.feup.casadamusica.services.ProductServices;
import feup.cmpv.feup.casadamusica.services.ShowServices;
import feup.cmpv.feup.casadamusica.structures.Product;
import feup.cmpv.feup.casadamusica.structures.Show;

public class BarProductsFragment extends Fragment implements IProductListener {

    private ListView listView;
    private ArrayAdapter<Product> adapter;
    private FloatingActionButton fab;

    public static Fragment getInstance() {
        Fragment fragment = new BarProductsFragment();
        Bundle b = new Bundle();
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_topic_fragment, container,false);
        InitializeView(view);
        return view;
    }

    private void ParseProducts(JSONObject products){
        try {
            JSONArray array = products.getJSONArray("products");
            for(int i = 0; i < array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                Product newProduct = new Product(
                        obj.getInt("id"),
                        obj.getString("name"),
                        (float)obj.getDouble("price")
                );
                newProduct.setProductListener(this);
                adapter.add(newProduct);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void RequestError(VolleyError error){

    }

    public void checkPurchase(FloatingActionButton button){
        if(button == null) return;
        fab = button;

        boolean showItems = false;

        for(int i = 0; i < adapter.getCount(); i++) {
            Product p = adapter.getItem(i);
            if (p.getQuantity() > 0) {
                showItems = true;
                break;
            }
        }

        if(showItems)
            button.show();
        else
            button.hide();
    }

    public ArrayList<Product> getPurchase(){
        ArrayList<Product> res = new ArrayList<>();
        for(int i = 0; i < adapter.getCount(); i++) {
            Product p = adapter.getItem(i);
            if(p.getQuantity() == 0 ) continue;

            Product np = new Product(p);
            np.setPrice(np.getPrice()*np.getQuantity());
            res.add(np);
        }
        return res;
    }

    private void InitializeView(View view){
        listView = view.findViewById(R.id.show_list_view);

        ArrayList<Product> productList = new ArrayList<>();
        adapter = new ProductListAdapter(view.getContext(), productList, R.layout.bar_list_item);
        listView.setAdapter(adapter);
        listView.computeScroll();

        ProductServices.GetProducts(
                    this::ParseProducts,
                    this::RequestError);

    }

    @Override
    public void onProductChanged(Product p) {
        checkPurchase(fab);
    }
}
