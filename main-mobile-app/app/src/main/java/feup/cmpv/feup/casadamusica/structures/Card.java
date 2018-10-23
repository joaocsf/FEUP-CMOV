package feup.cmpv.feup.casadamusica.structures;

public class Card {

    private String number;
    private int validity;
    private String type;

    public Card(){

    }

    public Card(String number, int validity, String type) {
        this.number = number;
        this.validity = validity;
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
