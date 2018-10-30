package feup.cmpv.feup.casadamusica.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Objects;

public class Utils {

    public static boolean verifyNetworkConnection(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public static String getUuid(Activity activity){
        return activity.getSharedPreferences(SecurityConstants.SHARED_PREFERANCES_FOLDER, Context.MODE_PRIVATE).getString(SecurityConstants.UUID, SecurityConstants.UUID_DEFAULT);
    }
}
