package feup.cmpv.feup.casadamusica.adapters.personal;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.List;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.structures.Product;
import feup.cmpv.feup.casadamusica.structures.VoucherGroup;

public class VoucherListAdapter extends ArrayAdapter<VoucherGroup> {
    private Context contex;
    private List<VoucherGroup> voucherGroups;
    private int layourID;

    public VoucherListAdapter(@NonNull Context context, @NonNull List<VoucherGroup> voucherGroups, @LayoutRes int layout_id){
        super(context, layout_id, voucherGroups);
        this.contex = context;
        this.voucherGroups = voucherGroups;
        this.layourID = layout_id;
    }

    private void increaseQuantity(VoucherGroup group, TextView tv, boolean increase){

        if(canIncrease(group) && canIncrease() && increase) {
            group.increaseQuantity();
        }else if(!increase)
            group.decreaseQuantity();

        tv.setText(group.getQuantity()+"");
    }

    private boolean canIncrease(VoucherGroup group){
        if(group.getProduct() == null) {
            return (group.getQuantity() < 1);
        }
        return group.getQuantity() < group.getProduct().getQuantity();
    }

    private boolean canIncrease(){
        int quantiy = 0;
       for(VoucherGroup group : voucherGroups){
            quantiy+=group.getQuantity();
       }
       return quantiy < 2;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null){
            listItem = LayoutInflater.from(contex).inflate(layourID, parent, false);
        }

        VoucherGroup currentGroup = voucherGroups.get(position);
        ImageView image = listItem.findViewById(R.id.voucher_list_item_image);
        TextView title = listItem.findViewById(R.id.voucher_list_item_title);
        TextView quantity = listItem.findViewById(R.id.voucher_list_item_quantity);

        ImageButton add = listItem.findViewById(R.id.voucher_list_item_add);
        ImageButton remove = listItem.findViewById(R.id.voucher_list_item_remove);

        TextView addQuantity = listItem.findViewById(R.id.voucher_list_item_add_quantity);

        if(image != null){
            if(currentGroup.getProduct() != null)
                Ion.with(image)
                        .error(R.drawable.ic_launcher_background)
                        .placeholder(R.drawable.ic_launcher_background).load(currentGroup.getProduct().getImage());
        }

        if(add != null){
            add.setOnClickListener((v)-> {
                increaseQuantity(currentGroup, addQuantity, true);
            });
        }
        if(remove != null){
            remove.setOnClickListener((v)-> {
                increaseQuantity(currentGroup, addQuantity, false);
            });
        }

        if(title != null) {
            if(currentGroup.getProduct()!= null)
                title.setText(currentGroup.getProduct().getName());
            else
                title.setText("5% Discount");
        }

        if(quantity != null)
            quantity.setText(currentGroup.getVoucherList().size() + "");

        return listItem;
    }
}
