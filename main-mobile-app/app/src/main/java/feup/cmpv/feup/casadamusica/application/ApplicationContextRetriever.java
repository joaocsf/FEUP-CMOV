package feup.cmpv.feup.casadamusica.application;

import android.app.Application;
import android.content.Context;

public class ApplicationContextRetriever extends Application{

    static Context ctx;

    @Override
    public void onCreate() {
        super.onCreate();
        ctx = getApplicationContext();
    }

    public static Context getContext(){
        return ctx;
    }
}
