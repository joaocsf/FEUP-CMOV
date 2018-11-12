package feup.cmpv.feup.casadamusica.fragments.bar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.adapters.bar.ProductListAdapter;
import feup.cmpv.feup.casadamusica.adapters.personal.VoucherListAdapter;
import feup.cmpv.feup.casadamusica.structures.Product;
import feup.cmpv.feup.casadamusica.structures.VoucherGroup;
import feup.cmpv.feup.casadamusica.utils.Archive;
import feup.cmpv.feup.casadamusica.utils.Utils;

import static feup.cmpv.feup.casadamusica.utils.Utils.df2;

public class BarPurchaseConfirmFragment extends DialogFragment {

    private List<Product> productList;
    private List<VoucherGroup> usedVouchers = new ArrayList<>();
    ArrayAdapter voucherListAdapter;
    TextView totalTV;

    public static BarPurchaseConfirmFragment getInstance(ArrayList<Product> products, float total, int orderNumber) {
        BarPurchaseConfirmFragment fragment = new BarPurchaseConfirmFragment();
        Bundle b = new Bundle();
        b.putFloat("total", total);
        b.putInt("order", orderNumber);
        b.putSerializable("products", products);
        fragment.setArguments(b);
        return fragment;
    }

    public static BarPurchaseConfirmFragment getInstance(ArrayList<Product> products, float total) {
        return getInstance(products, total, -1);
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
        List<String> vouchersToRemove = new ArrayList<>();
        try {

            osw = new OutputStreamWriter(byteArrayOutputStream, "ASCII");
            osw.write(Archive.getUuid());
            for(Product p : productList){
                osw.write('_');
                osw.write(p.getId());
                osw.write(':');
                osw.write(p.getQuantity());
            }
            for(VoucherGroup g : usedVouchers){
                for(int i = 0; i < g.getQuantity(); i++){
                    osw.write('_');
                    String voucherID = g.getVoucherList().get(i).getUuid();
                    osw.write(voucherID);
                    vouchersToRemove.add(voucherID);
                }
            }
            osw.flush();
            byteArrayOutputStream.flush();
            String orderString = byteArrayOutputStream.toString("ASCII");
            String orderSign = Archive.Sign(orderString);

            JSONObject object = new JSONObject();
            object.put("order", orderString);
            object.put("validation", orderSign);

            String msg = object.toString();

            Archive.removeVouchers(vouchersToRemove);

            Intent intent = Utils.initializeTransfer(msg, "order");

            startActivity(intent);
        } catch (Exception e){
            e.printStackTrace();
            Log.d("RESULT", "ERROR");
        }

    }

    public float calculateTotal(){
        float total = 0;
        for (Product p : productList) {
                total += p.getPrice();
        }
        boolean five_percent = false;
        for(VoucherGroup vg : usedVouchers){
            if(vg.getProduct() == null)
                five_percent = true;
            else{
                Product p = vg.getProduct();
                float price = p.getPrice()/p.getQuantity();
                float discountPrice = price * vg.getQuantity();
                total -= discountPrice;
            }
        }

        if(five_percent)
            return total *0.95f;

        return total;
    }

    public void InitializeView(View view){
        ListView listView = view.findViewById(R.id.bar_purchase_product_list);
        totalTV = view.findViewById(R.id.bar_purchase_total);
        TextView titleTV = view.findViewById(R.id.bar_purchase_recipt);

        Button cancel = view.findViewById(R.id.bar_purchase_cancel);
        cancel.setOnClickListener((click) -> {
            dismiss();
        });

        Button accept = view.findViewById(R.id.bar_purchase_confirm);
        accept.setOnClickListener((click) -> {
            generateByteOrder();
        });


        ListView voucherList = view.findViewById(R.id.bar_purchase_voucher_list);
        voucherListAdapter = new VoucherListAdapter(view.getContext(), new ArrayList<VoucherGroup>(), R.layout.voucher_simple_list_item);
        voucherList.setAdapter(voucherListAdapter);
        voucherList.computeScroll();

        productList = (ArrayList<Product>)getArguments().getSerializable("products");
        ProductListAdapter adapter = new ProductListAdapter(view.getContext(), productList , R.layout.purchase_product_list_item, false);
        listView.setAdapter(adapter);
        listView.computeScroll();

        ImageButton addVoucher = view.findViewById(R.id.bar_purchase_add_voucher);
        addVoucher.setOnClickListener((l) -> {
             BarAddVoucherFragment addVoucherFragment = BarAddVoucherFragment.getInstance((ArrayList<Product>)productList);
            addVoucherFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_CustomDialog);
            addVoucherFragment.show(getFragmentManager(), "Confirm Purchase");
            addVoucherFragment.setTargetFragment(this, 1);
        });

        int order = getArguments().getInt("order");
        if(order >= 0){
            titleTV.setText("Order #"+order);
        }

        float total = getArguments().getFloat("total");
        if(total <= 0f) {
            total = 0f;
            total = calculateTotal();
        }else {
            cancel.setVisibility(View.GONE);
            accept.setVisibility(View.GONE);
        }

        setTotal(total);
    }

    private void setTotal(float total){
        totalTV.setText(df2.format(total) + "€");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        usedVouchers = (List<VoucherGroup>)data.getSerializableExtra("vouchers");
        voucherListAdapter.clear();
        voucherListAdapter.addAll(usedVouchers);
        setTotal(calculateTotal());
    }

    @Override
    public void onStart() {
        super.onStart();
        Objects.requireNonNull(getDialog().getWindow())
                .setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }
}
