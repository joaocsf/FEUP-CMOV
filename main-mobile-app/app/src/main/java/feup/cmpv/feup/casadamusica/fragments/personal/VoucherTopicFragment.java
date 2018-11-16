package feup.cmpv.feup.casadamusica.fragments.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.adapters.personal.VoucherListAdapter;
import feup.cmpv.feup.casadamusica.services.VoucherServices;
import feup.cmpv.feup.casadamusica.structures.VoucherGroup;
import feup.cmpv.feup.casadamusica.utils.Archive;

public class VoucherTopicFragment extends Fragment{

    ArrayAdapter adapter;
    ListView listView;

    public static Fragment getInstance(){
        return new VoucherTopicFragment();
    }
    
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_topic_fragment, container,false);
        InitializeView(view);
        return view;
    }
    private void fetchVouchers(){

        VoucherServices.GetVouchers(
                (success) -> {
                    JSONArray vouchers = null;
                    try {
                        vouchers = success.getJSONArray("vouchers");
                        Archive.deleteAllVouchers();
                        Archive.addVouchers(vouchers);
                        List<VoucherGroup> voucherGroups = Archive.getAllVouchers();
                        adapter.clear();
                        adapter.addAll(voucherGroups);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, (failure) -> {
                }
        );
    }

    public void updateVouchers(boolean force){

        if (force){
            fetchVouchers();
            listView.smoothScrollToPosition(0);
            return;
        }

        adapter.clear();
        List<VoucherGroup> voucherGroups = Archive.getAllVouchers();
        adapter.addAll(voucherGroups);
    }

    private void InitializeView(View view) {

        listView = view.findViewById(R.id.show_list_view);

        List<VoucherGroup> voucherGroups = Archive.getAllVouchers();
        adapter = new VoucherListAdapter(view.getContext(), voucherGroups, R.layout.voucher_list_item);
        listView.setAdapter(adapter);
        listView.computeScroll();

        fetchVouchers();
    }
}
