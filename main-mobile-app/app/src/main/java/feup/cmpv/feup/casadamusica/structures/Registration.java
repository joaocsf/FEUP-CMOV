package feup.cmpv.feup.casadamusica.structures;

import java.io.Serializable;

public class Registration implements Serializable {

    private String name;
    private String username;
    private String password;
    private int nif;
    private String publicKey;
    private String cardType;
    private int cardNumber;
    private int cardValidationNumber;

    public Registration(String name, String username, String password, int nif, String cardType, int cardNumber, int cardValidationNumber) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.nif = nif;
        this.publicKey = null;
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.cardValidationNumber = cardValidationNumber;
    }

    public Registration(){
        this.name = null;
        this.username = null;
        this.password = null;
        this.publicKey = null;
        this.cardType = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCardValidationNumber() {
        return cardValidationNumber;
    }

    public void setCardValidationNumber(int cardValidationNumber) {
        this.cardValidationNumber = cardValidationNumber;
    }
}
