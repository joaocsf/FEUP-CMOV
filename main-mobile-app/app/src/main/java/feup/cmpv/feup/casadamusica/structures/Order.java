package feup.cmpv.feup.casadamusica.structures;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
    private int id;
    private float total;
    private String type;
    private String date;
    private String nif;
    ArrayList<Product> products = new ArrayList<>();
    ArrayList<ShowTickets> showTickets = new ArrayList<>();
    ArrayList<VoucherGroup> voucherGroups = new ArrayList<>();
    JSONObject obj;

    public Order(JSONObject obj, boolean parseAll){
        try {
           if(obj.has("id"))
               id = obj.getInt("id");
           if(obj.has("total"))
               total = (float)obj.getDouble("total");
           if(obj.has("type"))
               type = obj.getString("type");
           if(obj.has("date"))
               date = obj.getString("date");
           if(obj.has("nif"))
               nif = obj.getString("nif");
           if(parseAll){
               parseEverything(obj);
           }else
               this.obj = obj;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<ShowTickets> getShowTickets() {
        return showTickets;
    }

    public void setShowTickets(ArrayList<ShowTickets> showTickets) {
        this.showTickets = showTickets;
    }

    public ArrayList<VoucherGroup> getVoucherGroups() {
        return voucherGroups;
    }

    public void setVoucherGroups(ArrayList<VoucherGroup> voucherGroups) {
        this.voucherGroups = voucherGroups;
    }

    public void parseEverything(){
        parseEverything(obj);
        obj = null;
    }

    private void parseEverything(JSONObject obj){
        if(obj == null) return;

        try {
            if (obj.has("Vouchers")) {
                JSONArray array = obj.getJSONArray("Vouchers");
                for (int i = 0; i < array.length(); i++) {
                    VoucherGroup vg = new VoucherGroup(array.getJSONObject(i));
                    voucherGroups.add(vg);
                }
            }
            if (obj.has("Tickets")) {
                JSONObject tickets = obj.getJSONObject("Tickets");
                ShowTickets st = new ShowTickets(tickets);
                showTickets.clear();
                showTickets.add(st);
            }
            if (obj.has("Products")) {
                JSONArray array = obj.getJSONArray("Products");
                for (int i = 0; i < array.length(); i++) {
                    Product prod = new Product(array.getJSONObject(i));
                    products.add(prod);
                }
            }
        } catch (Exception e) {
        }
    }
}
