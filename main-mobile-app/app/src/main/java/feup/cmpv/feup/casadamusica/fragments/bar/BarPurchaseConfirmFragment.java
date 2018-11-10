package feup.cmpv.feup.casadamusica.fragments.bar;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.adapters.bar.ProductListAdapter;
import feup.cmpv.feup.casadamusica.services.OrderServices;
import feup.cmpv.feup.casadamusica.structures.Product;
import feup.cmpv.feup.casadamusica.utils.Archive;
import feup.cmpv.feup.casadamusica.view.NFC.NFCSendActivity;

public class BarPurchaseConfirmFragment extends DialogFragment {

    private List<Product> productList;

    public static BarPurchaseConfirmFragment getInstance(ArrayList<Product> products, float total) {
        BarPurchaseConfirmFragment fragment = new BarPurchaseConfirmFragment();
        Bundle b = new Bundle();
        b.putFloat("total", total);
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

    private void generateByteOrder(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        OutputStreamWriter osw = null;
        Log.d("RESULT", "Generating order");
        try {

            osw = new OutputStreamWriter(byteArrayOutputStream, "ASCII");
            osw.write(Archive.getUuid());
            for(Product p : productList){
                osw.write('_');
                osw.write(p.getId());
                osw.write(':');
                osw.write(p.getQuantity());
            }
            osw.flush();
            byteArrayOutputStream.flush();
            String orderString = byteArrayOutputStream.toString("ASCII");
            String orderSign = Archive.Sign(orderString);

            JSONObject object = new JSONObject();
            object.put("order", orderString);
            object.put("validation", orderSign);

            String msg = object.toString();

            Intent intent = new Intent(getContext(), NFCSendActivity.class);
            intent.putExtra("message", msg.getBytes());
            intent.putExtra("type", "order");
            startActivity(intent);

        } catch (Exception e){
            e.printStackTrace();
            Log.d("RESULT", "ERROR");
        }

    }

    public void InitializeView(View view){
        ListView listView = view.findViewById(R.id.bar_purchase_product_list);
        TextView totalTV = view.findViewById(R.id.bar_purchase_total);
        Button cancel = view.findViewById(R.id.bar_purchase_cancel);
        cancel.setOnClickListener((click) -> {
            dismiss();
        });
        Button accept = view.findViewById(R.id.bar_purchase_confirm);
        accept.setOnClickListener((click) -> {
            generateByteOrder();
        });


        productList = (ArrayList<Product>)getArguments().getSerializable("products");
        ProductListAdapter adapter = new ProductListAdapter(view.getContext(), productList , R.layout.purchase_product_list_item, false);
        listView.setAdapter(adapter);
        listView.computeScroll();

        float total = getArguments().getFloat("total");
        if(total <= 0f) {
            total = 0f;
            for (Product p : productList) {
                total += p.getPrice();
            }
        }else {
            cancel.setVisibility(View.GONE);
            accept.setVisibility(View.GONE);
        }

        totalTV.setText(total + "€");
    }
}
