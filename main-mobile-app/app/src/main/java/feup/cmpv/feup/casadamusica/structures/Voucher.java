package feup.cmpv.feup.casadamusica.structures;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Voucher implements Serializable {

    public static final String TABLE_NAME = "voucher";
    public static final String COLUMN_UUID = "uuid";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_PRODUCT_ID = "ProductId";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "( "
            + COLUMN_UUID + " STRING PRIMARY KEY, "
            + COLUMN_TYPE + " STRING, "
            + COLUMN_PRODUCT_ID + " INTEGER)";


    private String uuid;
    private String type;
    private int productID;


    public Voucher(){

    }


    public Voucher(JSONObject object){
        try {
            if(object.has("uuid"))
                this.uuid = object.getString("uuid");
            if(object.has("type"))
                this.type = object.getString("type");
            if(object.has("ProductId"))
                this.productID = object.getInt("ProductId");
        } catch (Exception e){
        }
    }

    public Voucher(String uuid, String type, int productID) {
        this.uuid = uuid;
        this.type = type;
        this.productID = productID;
    }

    public ContentValues getContentValues(){
        ContentValues values = new ContentValues();
        values.put(COLUMN_UUID, uuid);
        values.put(COLUMN_TYPE, type);
        values.put(COLUMN_PRODUCT_ID, productID);
        return values;
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    public JSONObject getJSONObject(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("uuid", uuid);
            obj.put("type", type);
            obj.put("ProductId", productID);
            return obj;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    @Override
    public String toString() {
        return "Voucher{" +
                "uuid='" + uuid + '\'' +
                ", type='" + type + '\'' +
                ", productID=" + productID +
                '}';
    }
}
