package feup.cmpv.feup.casadamusica.structures;

import java.io.Serializable;

public class Costumer implements Serializable {

    private String name;
    private String username;
    private String password;
    private String nif;
    private String publicKey;

    public Costumer(){

    }

    public Costumer(String name, String username, String password, String nif, String publicKey) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.nif = nif;
        this.publicKey = publicKey;
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

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
