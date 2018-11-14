package feup.cmpv.feup.casadamusica.structures;

import android.content.ContentValues;

import org.json.JSONObject;

import java.io.Serializable;

import feup.cmpv.feup.casadamusica.listeners.IProductListener;

public class Product implements Serializable {

    public static final String TABLE_NAME = "product";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_PRICE = "price";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_NAME + " STRING, "
            + COLUMN_IMAGE + " STRING, "
            + COLUMN_PRICE + " FLOAT)";

    private int id;
    private float price;
    private String name;
    private String image;
    private int quantity = 0;

    private IProductListener listener;

    public Product(JSONObject obj){
        try {
            if(obj.has("id"))
                id = obj.getInt("id");
            if(obj.has("image"))
                image = obj.getString("image");
            if(obj.has("name"))
                name = obj.getString("name");
            if(obj.has("price"))
                price = (float) obj.getDouble("price");
            if(obj.has("quantity"))
                quantity = obj.getInt("quantity");
        } catch (Exception e) {
        }
    }
    public Product(Product p){
        this.id = p.getId();
        this.name = p.getName();
        this.price = p.getPrice();
        this.quantity = p.getQuantity();
    }

    public Product(int id, String name, float price){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = 0;
    }

    public ContentValues getContentValues(){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_IMAGE, image);
        return values;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setProductListener(IProductListener listener){
        this.listener = listener;
    }

    private void alert(){
        if(listener != null)
            listener.onProductChanged(this);
    }

    public void Add(){
        quantity++;
        alert();
    }

    public void Remove(){
        quantity--;
        quantity = quantity < 0? 0 : quantity;
        alert();
    }

    public void Reset() {
        quantity = 0;
        alert();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", listener=" + listener +
                '}';
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        alert();
    }
}
