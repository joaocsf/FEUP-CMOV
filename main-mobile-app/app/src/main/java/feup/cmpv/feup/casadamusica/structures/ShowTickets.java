package feup.cmpv.feup.casadamusica.structures;

import java.io.Serializable;
import java.util.ArrayList;

public class ShowTickets implements Serializable {

    private Show show;
    private ArrayList<Ticket> tickets;

    public ShowTickets(Show show, ArrayList<Ticket> tickets) {
        this.show = show;
        this.tickets = tickets;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }
}
