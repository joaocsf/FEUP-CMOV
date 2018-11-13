package feup.cmpv.feup.casadamusica.structures;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class VoucherGroup implements Serializable {

    private Product product;
    private ArrayList<Voucher> voucherList = new ArrayList<>();
    private int quantity = 0;

    public VoucherGroup(Product p){
        product = p;
    }

    public VoucherGroup(JSONObject obj){
        try {

        } catch (Exception e) {

        }
    }

    public VoucherGroup(){

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

    public void increaseQuantity() {
        quantity++;
        quantity = Math.min(quantity, voucherList.size());
    }
    public void decreaseQuantity() {
        quantity--;
        quantity = Math.max(quantity, 0);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void splice() {
        ArrayList<Voucher> vouchers = new ArrayList<>();
        for(int i = 0; i < getQuantity(); i++){
            vouchers.add(voucherList.get(i));
        }
        voucherList = vouchers;
    }
}
