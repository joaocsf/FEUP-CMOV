package feup.cmpv.feup.casadamusica.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Objects;

import feup.cmpv.feup.casadamusica.application.ApplicationContextRetriever;
import feup.cmpv.feup.casadamusica.services.ShowServices;

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
        }
        return null;
    }
}
