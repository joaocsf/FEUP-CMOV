package feup.cmpv.feup.casadamusica.structures;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class VoucherGroup implements Serializable {

    private Product product;
    private ArrayList<Voucher> voucherList = new ArrayList<>();

    public VoucherGroup(Product p){
        product = p;
    }

    @Override
    public int hashCode() {
        return product.getId();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;

        if(!(obj instanceof VoucherGroup)) return false;
        return ((VoucherGroup) obj).getProduct().getId() == product.getId();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ArrayList<Voucher> getVoucherList() {
        return voucherList;
    }

    public void addVoucher(Voucher v){
        voucherList.add(v);
    }

}
