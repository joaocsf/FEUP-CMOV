package feup.cmpv.feup.casadamusica.structures;

public class Ticket {

    private String uuid;
    private Boolean used;
    private Integer seat;
    private Integer show_id;

    public Ticket(String uuid, Boolean used, Integer seat, Integer show_id) {
        this.uuid = uuid;
        this.used = used;
        this.seat = seat;
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

    public Integer getShow_id() {
        return show_id;
    }

    public void setShow_id(Integer show_id) {
        this.show_id = show_id;
    }
}
