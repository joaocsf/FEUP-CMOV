package feup.cmpv.feup.casadamusica.structures;

import org.json.JSONObject;

import java.io.Serializable;

public class Ticket implements Serializable {

    private String uuid;
    private Boolean used;
    private Integer seat;

    public Ticket(String uuid, Boolean used, Integer seat) {
        this.uuid = uuid;
        this.used = used;
        this.seat = seat;
    }

    public Ticket (JSONObject obj){
        try {
            if(obj.has("uuid"))
                uuid = obj.getString("uuid");
            if(obj.has("used"))
                used = obj.getBoolean("used");
            if(obj.has("seat"))
                seat = obj.getInt("seat");
        } catch (Exception e) {

        }
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
