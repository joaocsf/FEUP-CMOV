package feup.cmpv.feup.casadamusica.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import feup.cmpv.feup.casadamusica.application.ApplicationContextRetriever;
import feup.cmpv.feup.casadamusica.database.DBHelper;
import feup.cmpv.feup.casadamusica.structures.Product;
import feup.cmpv.feup.casadamusica.structures.Show;
import feup.cmpv.feup.casadamusica.structures.Ticket;
import feup.cmpv.feup.casadamusica.structures.Voucher;
import feup.cmpv.feup.casadamusica.structures.VoucherGroup;

public class Archive {

    private static String Escape(String name){
        return name.replaceAll("[\\/]","");
    }

    public static void SaveJSON(String fileName, JSONObject object){
        fileName = Escape(fileName);
        try {
            FileOutputStream outputStream = ApplicationContextRetriever.getContext().openFileOutput(fileName,Context.MODE_PRIVATE);
            byte[] bytes = object.toString().getBytes("utf-8");
            outputStream.write(bytes);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  static JSONObject LoadJSON(String fileName){
        fileName = Escape(fileName);
        try {
            FileInputStream inputStream = ApplicationContextRetriever.getContext().openFileInput(fileName);
            byte[] bytes = new byte[8000];
            int bytesRead;
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            while((bytesRead = inputStream.read(bytes)) != -1){
                bo.write(bytes, 0, bytesRead);
            }
            String jsonString = bo.toString("utf-8");
            return new JSONObject(jsonString);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static String getUuid(){
        return ApplicationContextRetriever.getContext().getSharedPreferences(SecurityConstants.SHARED_PREFERANCES_FOLDER, Context.MODE_PRIVATE).getString(SecurityConstants.UUID, SecurityConstants.UUID_DEFAULT);
    }

    public static void setUuid(String uuid){
        SharedPreferences.Editor editor = Objects.requireNonNull(ApplicationContextRetriever.getContext()).getSharedPreferences(SecurityConstants.SHARED_PREFERANCES_FOLDER, Context.MODE_PRIVATE).edit();
        editor.putString(SecurityConstants.UUID, uuid);
        editor.apply();
    }

    public static KeyStore.PrivateKeyEntry getEntry() throws Exception{
        KeyStore keyStore = KeyStore.getInstance(SecurityConstants.ANDROID_KEYSTORE);
        keyStore.load(null);
        return (KeyStore.PrivateKeyEntry)keyStore.getEntry(SecurityConstants.KEY_NAME, null);
    }

    public static String Sign(String uuid) {

        try {

            KeyStore.PrivateKeyEntry privateKeyEntry = getEntry();
            Signature s = Signature.getInstance(SecurityConstants.SIGN_ALGO);
            s.initSign(privateKeyEntry.getPrivateKey());
            s.update(uuid.getBytes("UTF-8"));
            byte[] signature = s.sign();
            //return new String(signature, "ASCII");
            return Base64.encodeToString(signature, Base64.NO_WRAP);

        } catch (Exception e) {
            return null;
        }
    }

    public static void deleteAllVouchers(){
        DBHelper db = new DBHelper(ApplicationContextRetriever.getContext());
        db.deleteAllVouchers();
    }

    public static void deleteAllTickets(){
        DBHelper db = new DBHelper(ApplicationContextRetriever.getContext());
        db.deleteAllTickets();
    }

    public static void deleteAllShows(){
        DBHelper db = new DBHelper(ApplicationContextRetriever.getContext());
        db.deleteAllShows();
    }

    public static List<Product> getAllProducts(){
        DBHelper db = new DBHelper(ApplicationContextRetriever.getContext());
        return db.getAllProducts();
    }

    public static List<Show> getAllShows(){
        DBHelper db = new DBHelper(ApplicationContextRetriever.getContext());
        return db.getAllShows();
    }

    public static List<Show> getAllPopularShows(){
        DBHelper db = new DBHelper(ApplicationContextRetriever.getContext());
        return db.getAllPopularShows();
    }

    public static void addProducts(JSONArray products){
        try {
            Log.d("Adding Voucher", "Adding VOUCHER");
            HashSet<Product> productHashSet = new HashSet<>();

            for (int i = 0; i < products.length(); i++) {
                JSONObject product = products.getJSONObject(i);
                Product p = new Product(product);
                productHashSet.add(p);
            }
            DBHelper db = new DBHelper(ApplicationContextRetriever.getContext());

            for (Product p : productHashSet) {
                db.insertProduct(p);
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public static void addVouchers(JSONArray vouchers){
        try {
            Log.d("Adding Voucher", "Adding VOUCHER");
            HashSet<Voucher> voucherHashSet = new HashSet<>();

            for (int i = 0; i < vouchers.length(); i++) {
                JSONObject voucher = vouchers.getJSONObject(i);
                Voucher v = new Voucher(voucher);
                voucherHashSet.add(v);
            }
            DBHelper db = new DBHelper(ApplicationContextRetriever.getContext());

            for (Voucher v : voucherHashSet) {
                Log.d("Adding Voucher", v +"");
                db.insertVoucher(v);
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public static void addTickets(JSONArray tickets){
        try {
            Log.d("Adding Ticket", "Adding Ticket");
            HashSet<Ticket> ticketHashSet = new HashSet<>();

            for (int i = 0; i < tickets.length(); i++) {
                JSONObject ticket = tickets.getJSONObject(i);
                Ticket t = new Ticket(ticket);
                ticketHashSet.add(t);
            }
            DBHelper db = new DBHelper(ApplicationContextRetriever.getContext());

            for (Ticket t : ticketHashSet) {
                Log.d("Adding Ticket", t +"");
                db.insertTicket(t);
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public static void addShows(JSONArray shows){
        try {
            Log.d("Adding Show", "Adding SHOW");
            HashSet<Show> showHashSet = new HashSet<>();

            for (int i = 0; i < shows.length(); i++) {
                JSONObject show = shows.getJSONObject(i);
                Show s = new Show(show);
                showHashSet.add(s);
            }
            DBHelper db = new DBHelper(ApplicationContextRetriever.getContext());

            for (Show s : showHashSet) {
                Log.d("Adding Voucher", s +"");
                db.insertShow(s);
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }


    public static Set<VoucherGroup> GetProductVouchers(){
        DBHelper db = new DBHelper(ApplicationContextRetriever.getContext());
        return db.getAllProductVouchers();
    }

    public static VoucherGroup GetDiscountVouchers(){
        DBHelper db = new DBHelper(ApplicationContextRetriever.getContext());
        return db.getAllDiscountVouchers();
    }

    public static List<VoucherGroup> getAllVouchers(){
        DBHelper db = new DBHelper(ApplicationContextRetriever.getContext());
        return db.getAllVouchers();
    }
    public static boolean hasKey(){
        try {
            KeyStore keyStore = KeyStore.getInstance(SecurityConstants.ANDROID_KEYSTORE);
            keyStore.load(null);
            Boolean registered = keyStore.containsAlias(SecurityConstants.KEY_NAME);
            return registered;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String publickKey64(){

        try {
            KeyStore keyStore = KeyStore.getInstance(SecurityConstants.ANDROID_KEYSTORE);
            keyStore.load(null);
            Certificate certificate = keyStore.getCertificate(SecurityConstants.KEY_NAME);
            PublicKey pk = certificate.getPublicKey();
            return Base64.encodeToString(pk.getEncoded(), Base64.DEFAULT);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static void removeVouchers(List<String> vouchersToRemove) {
        DBHelper db = new DBHelper(ApplicationContextRetriever.getContext());
        db.deleteVouchers(vouchersToRemove);
    }
}
