package feup.cmpv.feup.casadamusica.structures;

import android.content.ContentValues;

import org.json.JSONObject;

import java.io.Serializable;

public class Ticket implements Serializable {

    public static final String TABLE_NAME = "ticket";
    public static final String COLUMN_UUID = "uuid";
    public static final String COLUMN_SEAT = "seat";
    public static final String COLUMN_USED = "used";
    public static final String COLUMN_SHOW_ID = "showId";


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_UUID + " INTEGER PRIMARY KEY, "
                    + COLUMN_SEAT + " INTEGER, "
                    + COLUMN_USED + " BOOLEAN, "
                    + COLUMN_SHOW_ID + " STRING)";

    private String uuid;
    private Boolean used;
    private Integer seat;
    private Integer showId;

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
            if(obj.has("ShowId"))
                showId = obj.getInt("ShowId");
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

    public Integer getShowId() {
        return showId;
    }

    public void setShowId(Integer showId) {
        this.showId = showId;
    }

    public ContentValues getContentValues(){
        ContentValues values = new ContentValues();
        values.put(COLUMN_UUID, this.uuid);
        values.put(COLUMN_SEAT, this.seat);
        values.put(COLUMN_USED, this.used);
        values.put(COLUMN_SHOW_ID, this.showId);
        return values;
    }

}
