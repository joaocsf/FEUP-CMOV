package feup.cmpv.feup.casadamusica.services;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import feup.cmpv.feup.casadamusica.application.ApplicationContextRetriever;

public class Api {

    private static RequestQueue queue;
    private static final String host = "http://192.168.1.194:8080";


    private static RequestQueue getQueue(){
        if(queue == null){
            queue = Volley.newRequestQueue(ApplicationContextRetriever.getContext());
        }
        return queue;
    }

}
