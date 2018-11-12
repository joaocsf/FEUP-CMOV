package feup.cmpv.feup.casadamusica.fragments.bar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.adapters.personal.VoucherListAdapter;
import feup.cmpv.feup.casadamusica.structures.Product;
import feup.cmpv.feup.casadamusica.structures.VoucherGroup;
import feup.cmpv.feup.casadamusica.utils.Archive;

public class BarAddVoucherFragment extends DialogFragment {

    private List<Product> productList;

    private List<VoucherGroup> voucherGroups;

    public static BarAddVoucherFragment getInstance(ArrayList<Product> products) {
        BarAddVoucherFragment fragment = new BarAddVoucherFragment();
        Bundle b = new Bundle();
        b.putSerializable("products", products);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bar_purchase_voucher_fragment, container,false);
        InitializeView(view);
        return view;
    }

    private List<VoucherGroup> getCompatibleVouchers(List<Product> products){
        List<VoucherGroup> groups = Archive.getAllVouchers();
        HashMap<Integer, Product> productIds = new HashMap<>();

        for(Product p : products){
            productIds.put(p.getId(), p);
        }

        List<VoucherGroup> result = new ArrayList<>();
        for(VoucherGroup g : groups){
            if(g.getProduct() == null){
                result.add(g);
                continue;
            }
            if(!productIds.containsKey(g.getProduct().getId())) continue;
            g.setProduct(productIds.get(g.getProduct().getId()));
            result.add(g);
        }

        return result;
    }

    public ArrayList<VoucherGroup> usedVouchers(){
        ArrayList<VoucherGroup> vg = new ArrayList<>();
        for(VoucherGroup g : voucherGroups){
            if(g.getQuantity() > 0) {
                g.splice();
                vg.add(g);
            }
        }
        return vg;
    }

    public void InitializeView(View view){
        ListView listView = view.findViewById(R.id.bar_purchase_voucher_list);

        Button confirm = view.findViewById(R.id.bar_voucher_confirm);

        Button cancel = view.findViewById(R.id.bar_voucher_cancel);

        cancel.setOnClickListener((click) -> {
            dismiss();
        });

        confirm.setOnClickListener((click) -> {
            Intent intent = new Intent();
            intent.putExtra("vouchers", usedVouchers());
            getTargetFragment().onActivityResult(0,0, intent);
            dismiss();
        });

        productList = (ArrayList<Product>)getArguments().getSerializable("products");
        voucherGroups = getCompatibleVouchers(productList);

        VoucherListAdapter adapter = new VoucherListAdapter(view.getContext(), voucherGroups, R.layout.voucher_select_list_item);

        listView.setAdapter(adapter);
        listView.computeScroll();
    }
}
