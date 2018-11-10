package feup.cmpv.feup.casadamusica.view.NFC;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.fragments.bar.BarPurchaseConfirmFragment;
import feup.cmpv.feup.casadamusica.services.OrderServices;
import feup.cmpv.feup.casadamusica.structures.Product;

public class NFCReceiveOrderActivity extends NFCReceiveActivity{
    TextView tv;
    @Override
    protected void onDataReceived(String data) {
        try {
            JSONObject object = new JSONObject(data);
            String order = object.getString("order");
            String validation = object.getString("validation");
            OrderServices.Order(order, validation,
                    (obj)-> {
                parseOrder(obj);
                },
                    (error)->{
                tv.setText("FAILURE");
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        tv.setText(data);
    }

    private void parseOrder(JSONObject response) {
        try {

            ArrayList<Product> products = new ArrayList<Product>();
            JSONArray array = response.getJSONArray("productSummary");

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                int id = obj.getInt("id");
                String name = obj.getString("name");
                int quantity = obj.getInt("quantity");
                float price = (float) obj.getDouble("price");

                Product p = new Product(id, name, price);
                p.setQuantity(quantity);
                products.add(p);
            }
            float total = (float)response.getDouble("total");
            BarPurchaseConfirmFragment confirmPurchase = BarPurchaseConfirmFragment.getInstance(products, total);
            confirmPurchase.show(getSupportFragmentManager(), "Confirm Order");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_receive_activity);
        tv = findViewById(R.id.nfc_validating_order);
    }
}
