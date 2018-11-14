package feup.cmpv.feup.casadamusica.fragments.bar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import feup.cmpv.feup.casadamusica.adapters.orders.OrderListAdapter;
import feup.cmpv.feup.casadamusica.listeners.IProductListener;
import feup.cmpv.feup.casadamusica.services.OrderServices;
import feup.cmpv.feup.casadamusica.services.ProductServices;
import feup.cmpv.feup.casadamusica.structures.Order;
import feup.cmpv.feup.casadamusica.structures.Product;
import feup.cmpv.feup.casadamusica.utils.Archive;
import feup.cmpv.feup.casadamusica.view.OrderActivity;

public class BarPurchasesFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ArrayAdapter<Order> adapter;

    public static Fragment getInstance() {
        Fragment fragment = new BarPurchasesFragment();
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

    private void ParseOrders(JSONObject orders){
        if(orders == null) return;
        adapter.clear();
        try {
            Archive.SaveJSON("orders", orders);
            JSONArray array = orders.getJSONArray("orders");
            adapter.clear();
            ArrayList<Order> orderList = new ArrayList<>();
            for(int i = 0; i < array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                orderList.add(new Order(obj, false));
            }
            adapter.addAll(orderList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updatePurchases(){
         OrderServices.GetOrders(
            this::ParseOrders,
            this::RequestError);
    }

    private void RequestError(VolleyError error){
        ParseOrders(Archive.LoadJSON("orders"));
    }

    private void InitializeView(View view){
        listView = view.findViewById(R.id.show_list_view);

        ArrayList<Order> orderList = new ArrayList<>();
        adapter = new OrderListAdapter(view.getContext(), orderList);
        listView.setAdapter(adapter);
        listView.computeScroll();
        listView.setOnItemClickListener(this);
        updatePurchases();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), OrderActivity.class);
        intent.putExtra("order", adapter.getItem(position));
        startActivity(intent);
    }
}
