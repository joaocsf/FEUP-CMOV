package feup.cmpv.feup.casadamusica.adapters.personal;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.structures.VoucherGroup;
import feup.cmpv.feup.casadamusica.structures.Voucher;

public class VoucherListAdapter extends ArrayAdapter<VoucherGroup> {
    private Context contex;
    private List<VoucherGroup> voucherGroups;

    public VoucherListAdapter(@NonNull Context context, @NonNull List<VoucherGroup> voucherGroups){
        super(context, R.layout.ticket_list_item, voucherGroups);
        this.contex = context;
        this.voucherGroups = voucherGroups;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null){
            listItem = LayoutInflater.from(contex).inflate(R.layout.ticket_list_item, parent, false);
        }

        VoucherGroup currentGroup = voucherGroups.get(position);
        ImageView image = listItem.findViewById(R.id.ticket_list_item_image);
        TextView title = listItem.findViewById(R.id.ticket_list_item_title);
        TextView date = listItem.findViewById(R.id.ticket_list_item_date);
        TextView seats = listItem.findViewById(R.id.ticket_list_item_seats);



        if(title != null) {
            if(currentGroup.getProduct()!= null)
                title.setText(currentGroup.getProduct().getName());
            else
                title.setText("5% Discount");
        }

        if(date != null)
            date.setText("");

        if(seats != null)
            seats.setText(currentGroup.getVoucherList().size() + "");

        return listItem;
    }
}
