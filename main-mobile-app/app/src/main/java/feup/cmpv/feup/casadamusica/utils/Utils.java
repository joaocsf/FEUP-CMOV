package feup.cmpv.feup.casadamusica.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.NfcAdapter;

import java.util.Objects;

import feup.cmpv.feup.casadamusica.application.ApplicationContextRetriever;

public class Utils {

    public static boolean verifyNetworkConnection(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public static boolean canUseNFC(){
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(ApplicationContextRetriever.getContext());
        return nfcAdapter != null && nfcAdapter.isEnabled();
    }


}
