package feup.cmpv.feup.casadamusica.structures;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class ShowTickets implements Serializable {

    private Show show;
    private ArrayList<Ticket> tickets;

    public ShowTickets(Show show, ArrayList<Ticket> tickets) {
        this.show = show;
        this.tickets = tickets;
    }

    public ShowTickets(JSONObject obj){
        try {
            show = new Show(obj);
            tickets = new ArrayList<>();

            if(obj.has("Tickets")) {
                JSONArray ticketSets = obj.getJSONArray("Tickets");

                for (int k = 0; k < ticketSets.length(); k++) {
                    JSONObject ticket = ticketSets.getJSONObject(k);

                    Ticket newTicket = new Ticket(ticket);
                    tickets.add(newTicket);
                }
            }
        } catch (Exception e) {
        }
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
