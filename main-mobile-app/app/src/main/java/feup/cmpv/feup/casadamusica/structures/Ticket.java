package feup.cmpv.feup.casadamusica.structures;

public class Ticket {

    private String uuid;
    private Boolean used;
    private Integer seat;

    public Ticket(String uuid, Boolean used, Integer seat) {
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

}
