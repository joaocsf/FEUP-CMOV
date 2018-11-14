package feup.cmpv.feup.casadamusica.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import feup.cmpv.feup.casadamusica.structures.Product;
import feup.cmpv.feup.casadamusica.structures.Show;
import feup.cmpv.feup.casadamusica.structures.ShowTickets;
import feup.cmpv.feup.casadamusica.structures.Ticket;
import feup.cmpv.feup.casadamusica.structures.Voucher;
import feup.cmpv.feup.casadamusica.structures.VoucherGroup;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "casa_da_musica";

    public DBHelper(@Nullable Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Voucher.CREATE_TABLE);
        db.execSQL(Product.CREATE_TABLE);
        db.execSQL(Ticket.CREATE_TABLE);
        db.execSQL(Show.CREATE_TABLE);
    }

    private void dropTable(SQLiteDatabase db, String tableName){
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
    }

    private void dropTables(SQLiteDatabase db){
        dropTable(db, Voucher.TABLE_NAME);
        dropTable(db, Product.TABLE_NAME);
        dropTable(db, Show.TABLE_NAME);
        dropTable(db, Ticket.TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTables(db);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTables(db);
        onCreate(db);
    }

    public long insertProduct(Product product){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = product.getContentValues();
        long id = db.replace(Product.TABLE_NAME,null, values);
        db.close();
        return id;
    }

    public long insertVoucher(Voucher voucher){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = voucher.getContentValues();
        long id = db.replace(Voucher.TABLE_NAME,null, values);
        db.close();
        return id;
    }

    public long insertTicket(Ticket ticket){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = ticket.getContentValues();
        long id = db.replace(Ticket.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public long insertShow(Show show){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = show.getContentValues();
        long id = db.replace(Show.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public List<VoucherGroup> getAllVouchers() {
        List<VoucherGroup> voucherGroups = new ArrayList<>();
        VoucherGroup discount = getAllDiscountVouchers();

        if(discount.getVoucherList().size() > 0)
            voucherGroups.add(getAllDiscountVouchers());

        voucherGroups.addAll(getAllProductVouchers());
        return voucherGroups;
    }

    public VoucherGroup getAllDiscountVouchers() {
        HashSet<Voucher> voucherSet = new HashSet<>();
         String selectQuery = "SELECT * FROM "
            + Voucher.TABLE_NAME
            + " WHERE " + Voucher.COLUMN_TYPE+" LIKE '%discount%'";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        VoucherGroup vg = new VoucherGroup();

        if(cursor.moveToFirst()){
            do {

                String uuid = cursor.getString(cursor.getColumnIndex(Voucher.COLUMN_UUID));
                String type = cursor.getString(cursor.getColumnIndex(Voucher.COLUMN_TYPE));
                Voucher v = new Voucher(uuid, type, -1);
                vg.addVoucher(v);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return vg;
    }

    public Set<VoucherGroup> getAllProductVouchers(){
        HashMap<VoucherGroup, VoucherGroup> productVouchers = new HashMap<>();

        String selectQuery = "SELECT * FROM "
                + Voucher.TABLE_NAME + " JOIN " + Product.TABLE_NAME
                + " ON " + Voucher.TABLE_NAME+"."+Voucher.COLUMN_PRODUCT_ID+"="+Product.TABLE_NAME+"."+Product.COLUMN_ID
                + " WHERE " + Voucher.TABLE_NAME+"."+Voucher.COLUMN_TYPE+" like '%product%'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                int product_id = cursor.getInt(cursor.getColumnIndex(Product.COLUMN_ID));
                String product_name = cursor.getString(cursor.getColumnIndex(Product.COLUMN_NAME));
                float product_price = (float)cursor.getDouble(cursor.getColumnIndex(Product.COLUMN_PRICE));

                Product product = new Product(product_id, product_name, product_price);

                VoucherGroup pvouchers = new VoucherGroup(product);

                if(productVouchers.containsKey(pvouchers))
                    pvouchers = productVouchers.get(pvouchers);
                else
                    productVouchers.put(pvouchers, pvouchers);

                String uuid = cursor.getString(cursor.getColumnIndex(Voucher.COLUMN_UUID));
                String type = cursor.getString(cursor.getColumnIndex(Voucher.COLUMN_TYPE));
                Voucher v = new Voucher(uuid, type, product_id);
                pvouchers.addVoucher(v);

            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return productVouchers.keySet();
    }

    public List<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        String selectQuery = "SELECT * FROM "
           + Product.TABLE_NAME;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {

                int id = cursor.getInt(cursor.getColumnIndex(Product.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(Product.COLUMN_NAME));
                float price = cursor.getFloat(cursor.getColumnIndex(Product.COLUMN_PRICE));
                Product p = new Product(id, name, price);
                products.add(p);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return products;
    }

    public List<Show> getAllShows() {
        ArrayList<Show> shows = new ArrayList<>();
        String selectQuery = "SELECT * FROM "
                + Show.TABLE_NAME
                + " ORDER BY "+ Show.COLUMN_DATE  + " DESC ";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {

                int id = cursor.getInt(cursor.getColumnIndex(Show.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(Show.COLUMN_NAME));
                String date = cursor.getString(cursor.getColumnIndex(Show.COLUMN_DATE));
                float price = cursor.getFloat(cursor.getColumnIndex(Show.COLUMN_PRICE));
                int atendees = cursor.getInt(cursor.getColumnIndex(Show.COLUMN_ATENDEES));
                int duration = cursor.getInt(cursor.getColumnIndex(Show.COLUMN_DURATION));

                Show s = new Show(id, name, date, price,atendees, duration);
                shows.add(s);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return shows;
    }

    public List<Show> getAllPopularShows() {
        ArrayList<Show> shows = new ArrayList<>();
        String selectQuery = "SELECT * FROM "
                + Show.TABLE_NAME
                + " ORDER BY " + Show.COLUMN_ATENDEES + " DESC ";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {

                int id = cursor.getInt(cursor.getColumnIndex(Show.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(Show.COLUMN_NAME));
                String date = cursor.getString(cursor.getColumnIndex(Show.COLUMN_DATE));
                float price = cursor.getFloat(cursor.getColumnIndex(Show.COLUMN_PRICE));
                int atendees = cursor.getInt(cursor.getColumnIndex(Show.COLUMN_ATENDEES));
                int duration = cursor.getInt(cursor.getColumnIndex(Show.COLUMN_DURATION));

                Show s = new Show(id, name, date, price,atendees, duration);
                shows.add(s);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return shows;
    }

    public Set<ShowTickets> getAllShowTickets() {
        HashMap<ShowTickets, ShowTickets> showTickets = new HashMap<>();

        String selectQuery = "SELECT * FROM "
                + Show.TABLE_NAME + " JOIN " + Ticket.TABLE_NAME
                + " ON " + Show.TABLE_NAME+"."+Show.COLUMN_ID+"="+Ticket.TABLE_NAME+"."+Ticket.COLUMN_SHOW_ID
                + " WHERE " + Ticket.TABLE_NAME+"."+Ticket.COLUMN_USED+"=" + "0"
                + " ORDER BY " + Ticket.TABLE_NAME+"."+Ticket.COLUMN_UUID;

        Log.d("Query" , selectQuery);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndex(Show.COLUMN_ID));
                String uuid = cursor.getString(cursor.getColumnIndex(Ticket.COLUMN_UUID));
                String name = cursor.getString(cursor.getColumnIndex(Show.COLUMN_NAME));
                String date = cursor.getString(cursor.getColumnIndex(Show.COLUMN_DATE));
                float price = cursor.getFloat(cursor.getColumnIndex(Show.COLUMN_PRICE));
                int atendees = cursor.getInt(cursor.getColumnIndex(Show.COLUMN_ATENDEES));
                int duration = cursor.getInt(cursor.getColumnIndex(Show.COLUMN_DURATION));

                Show s = new Show(id, name, date, price,atendees, duration);

                ShowTickets st = new ShowTickets(s);

                if(showTickets.containsKey(st))
                    st = showTickets.get(st);
                else
                    showTickets.put(st, st);


                int seat = cursor.getInt(cursor.getColumnIndex(Ticket.COLUMN_SEAT));

                Ticket ticket = new Ticket(uuid, false,seat);
                st.addTicket(ticket);

            }while (cursor.moveToNext());
        }

        System.out.println("show tickets size = " + showTickets.keySet().size());
        cursor.close();
        db.close();

        return showTickets.keySet();
    }

    public void deleteAllTickets() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Ticket.TABLE_NAME, null, null);
        db.close();
    }

    public void deleteAllShows() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Show.TABLE_NAME, null, null);
        db.close();
    }

    public void deleteTicket(String uuid){
        SQLiteDatabase db = getWritableDatabase();
        int i = db.delete(Ticket.TABLE_NAME, Ticket.COLUMN_UUID + "=?", new String[]{uuid});
        Log.d("Size", i + "");
        db.close();
    }

    public void deleteShow(String id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Show.TABLE_NAME, Show.COLUMN_ID+ "=?", new String[]{id});
        db.close();
    }

    public void deleteAllVouchers() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Voucher.TABLE_NAME, null, null);
        db.close();
    }

    public void deleteVoucher(String uuid){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Voucher.TABLE_NAME, Voucher.COLUMN_UUID + "=?", new String[]{uuid});
        db.close();
    }

    public void deleteVouchers(List<String> vouchersToRemove) {
        for(String uuid : vouchersToRemove){
            deleteVoucher(uuid);
        }
    }

    public void deleteTickets(ArrayList<String> ticketsToRemove) {
        for(String uuid : ticketsToRemove){
            deleteTicket(uuid);
        }
    }
}
