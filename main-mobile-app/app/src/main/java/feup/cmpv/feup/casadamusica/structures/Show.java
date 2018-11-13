package feup.cmpv.feup.casadamusica.structures;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Show implements Serializable {
    private String name;
    private String date;
    private float price;
    private int atendees;
    private int duration;
    private String id;

    public Show(String id, String name, String date, float price, int atendees, int duration) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.atendees = atendees;

        Pattern p  = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}).*");
        Matcher m = p.matcher(date);
        if(m.find()){
            this.date = m.group(1);
        }else
            this.date = date;

        this.duration = duration;
    }

    public Show(JSONObject obj){

        try {
            if(obj.has("id"))
                id = obj.getString("id");
            if(obj.has("name"))
                name = obj.getString("name");
            if(obj.has("date"))
                date = obj.getString("date").split("T")[0];
            if(obj.has("price"))
                price = (float) obj.getDouble("price");
            if(obj.has("atendees"))
                atendees = obj.getInt("atendees");
            if(obj.has("duration"))
                duration = obj.getInt("duration");
        } catch (Exception e){
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAtendees() {
        return atendees;
    }

    public void setAtendees(int atendees) {
        this.atendees = atendees;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
