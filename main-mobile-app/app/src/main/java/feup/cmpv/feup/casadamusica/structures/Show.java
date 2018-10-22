package feup.cmpv.feup.casadamusica.structures;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Show {
    public String name;
    public String date;
    public float price;
    public int atendees;

    public Show(String name, String date, float price, int atendees) {
        this.name = name;
        this.price = price;
        this.atendees = atendees;

        Pattern p  = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}).*");
        Matcher m = p.matcher(date);
        if(m.find()){
            this.date = m.group(1);
        }else
            this.date = date;
    }
}
