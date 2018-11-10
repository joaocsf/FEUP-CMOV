package feup.cmpv.feup.casadamusica.structures;

import java.io.Serializable;

import feup.cmpv.feup.casadamusica.listeners.IProductListener;

public class Product implements Serializable {

    private int id;
    private float price;
    private String name;
    private int quantity = 0;

    private IProductListener listener;

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
