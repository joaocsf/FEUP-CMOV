package feup.cmpv.feup.casadamusica.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Objects;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.structures.ShowTickets;


public class QRCodeUtils {

    private static final int DIMENSION =  500;

    public static Bitmap encodeAsBitmap(String str, Context context) throws WriterException {
        BitMatrix result;

        try {
            result = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, DIMENSION, DIMENSION, null);
        }
        catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }

        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ?  context.getResources().getColor(R.color.black): context.getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
        return bitmap;
    }

    public static String generateMessage(ShowTickets showTickets, int numberOfTickets){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        OutputStreamWriter osw;
        JSONObject object = null;

        try {
            osw = new OutputStreamWriter(byteArrayOutputStream, "ASCII");
            osw.write(Archive.getUuid());
            osw.write("|");
            osw.write(numberOfTickets);
            osw.write("|");

            for(int i =0; i < numberOfTickets; i++){
                osw.write(showTickets.getTickets().get(i).getUuid());
                osw.write("|");
            }

            osw.write(showTickets.getShow().getId());

            osw.flush();
            byteArrayOutputStream.flush();

            String ticketsString = byteArrayOutputStream.toString();
            String ticketsSign = Archive.Sign(ticketsString);

            object = new JSONObject();
            object.put("tickets", ticketsString);
            object.put("validation", ticketsSign);

            return object.toString();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return Objects.requireNonNull(object).toString();
    }
}
