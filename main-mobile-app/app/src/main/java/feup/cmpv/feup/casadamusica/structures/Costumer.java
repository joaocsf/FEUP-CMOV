package feup.cmpv.feup.casadamusica.structures;

public class Costumer {

    private String name;
    private String username;
    private String password;
    private int nif;
    private String publicKey;

    public Costumer(String name, String username, String password, int nif, String publicKey) {
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
}
