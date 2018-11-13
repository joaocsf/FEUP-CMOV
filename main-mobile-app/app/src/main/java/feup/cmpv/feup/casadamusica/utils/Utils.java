package feup.cmpv.feup.casadamusica.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.NfcAdapter;

import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;

import feup.cmpv.feup.casadamusica.application.ApplicationContextRetriever;
import feup.cmpv.feup.casadamusica.structures.Order;
import feup.cmpv.feup.casadamusica.view.NFC.NFCSendActivity;
import feup.cmpv.feup.casadamusica.view.OrderActivity;
import feup.cmpv.feup.casadamusica.view.QRActivity;

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

    public static Intent initializeTransfer(String msg, String mimType){
        java.lang.Class<?> activity = Utils.canUseNFC()? NFCSendActivity.class : QRActivity.class;

        Intent intent = new Intent(ApplicationContextRetriever.getContext(), activity);
        intent.putExtra("message", msg.getBytes());
        intent.putExtra("type", mimType);
        return  intent;
    }

    public static DecimalFormat df2 = new DecimalFormat("0.00");

    public static String formatDate(String date){
        return date.replaceAll("T|\\.\\d+Z"," ").trim();
    }

    public static Intent OpenOrderActivity(JSONObject obj){
        Order order = new Order(obj, true);
        Intent intent = new Intent(ApplicationContextRetriever.getContext(), OrderActivity.class);
        intent.putExtra("order", order);
        return intent;
    }
}
