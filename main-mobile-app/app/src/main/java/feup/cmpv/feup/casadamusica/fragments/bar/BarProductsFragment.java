package feup.cmpv.feup.casadamusica.fragments.bar;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.adapters.bar.ProductListAdapter;
import feup.cmpv.feup.casadamusica.adapters.show.ShowListAdapter;
import feup.cmpv.feup.casadamusica.services.ProductServices;
import feup.cmpv.feup.casadamusica.services.ShowServices;
import feup.cmpv.feup.casadamusica.structures.Product;
import feup.cmpv.feup.casadamusica.structures.Show;

public class BarProductsFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<Product> adapter;

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
                adapter.add(newProduct);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void RequestError(VolleyError error){

    }

    private void InitializeView(View view){
        listView = view.findViewById(R.id.show_list_view);

        ArrayList<Product> productList = new ArrayList<>();
        adapter = new ProductListAdapter(view.getContext(), productList);
        listView.setAdapter(adapter);
        listView.computeScroll();

        ProductServices.GetProducts(
                    this::ParseProducts,
                    this::RequestError);

    }
}
