package feup.cmpv.feup.casadamusica.fragments.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.adapters.personal.VoucherListAdapter;
import feup.cmpv.feup.casadamusica.services.ProductServices;
import feup.cmpv.feup.casadamusica.structures.VoucherGroup;
import feup.cmpv.feup.casadamusica.utils.Archive;

public class VoucherTopicFragment extends Fragment{

    ArrayAdapter adapter;

    public static Fragment getInstance(){
        return new VoucherTopicFragment();
    }
    
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_topic_fragment, container,false);
        InitializeView(view);
        return view;
    }

    public void updateVouchers(){
        List<VoucherGroup> voucherGroups = Archive.GetAllVouchers();
        adapter.clear();
        adapter.addAll(voucherGroups);
    }

    private void InitializeView(View view) {

        ListView listView = view.findViewById(R.id.show_list_view);

        List<VoucherGroup> voucherGroups = Archive.GetAllVouchers();
        adapter = new VoucherListAdapter(view.getContext(), voucherGroups);
        listView.setAdapter(adapter);
        listView.computeScroll();
    }
}
