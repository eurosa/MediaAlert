package com.example.aatick.medialart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by MSI PC on 10/6/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

private static  final int DATABASE_VERSION=4;
    private static final String DATABASE_NAME="mediaalertstore.db";
    private static final String DATABASE_TABLE="media_store";
    private static final String DATABASE_TABLE2="media_store2";
    private static final String DATABASE_TABLE_FOLLOW_UP="media_follower";
    private static final String SMS_TABLE="sms_table";
    private static final String ALERT_ID="id";
    private static final String MEDICINE_NAME="medicine_name";
    private static final String TIME="time";
    private static final String DATE="date";
    private static final String DOSAGE="dosage";
    private static final String QUANTITY="quantity";
    private static final String STOCK="stock";
    private static final String TIME_INTERVAL="time_interval";
    private static final String DESCRIPTION="description";
    //doctor follow up information
    private static final String FOLLOW_ID="id";
    private static final String DOCTOR_NAME="doctor_name";
    private static final String FOLLOW_DATE="follow_date";
    private static final String FOLLOW_TIME="follow_time";
    private static final String DOCTOR_LOCATION="doctor_location";
    private static final String DISEASE_NAME="disease_name";
    private static final String DESCRIPTION_FOLLOW="description";

    //database for sms

    private static final String SMS_ID="id";
    private static final String SMS_TIME="sms_time";
    private static final String SMS_DATE="sms_date";
    private static final String SMS_MOBILE="sms_mobile";
    private static final String SMS_DESCRIPTION="sms_description";
    private static final String SMS_MEDICINE="sms_medicine";

    private static final String[] COLUMNS={ALERT_ID,MEDICINE_NAME,TIME,DATE,DOSAGE,QUANTITY,STOCK,TIME_INTERVAL,DESCRIPTION};
    private static final String CREATE_MEDIA_ALERT_TABLE="CREATE TABLE "+DATABASE_TABLE+"("+ALERT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+MEDICINE_NAME+" TEXT,"+DATE+" TEXT,"+TIME+" TEXT,"+DOSAGE+" TEXT,"+
        QUANTITY+" TEXT,"+STOCK+" TEXT,"+TIME_INTERVAL+" TEXT,"+DESCRIPTION+" TEXT"+")";

    private static final String[] COLUMNS2={ALERT_ID,MEDICINE_NAME,TIME,DATE,DOSAGE,QUANTITY,STOCK,TIME_INTERVAL,DESCRIPTION};

    private static final String CREATE_MEDIA_ALERT_TABLE2="CREATE TABLE "+DATABASE_TABLE2+"("+ALERT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+MEDICINE_NAME+" TEXT,"+DATE+" TEXT,"+TIME+" TEXT,"+DOSAGE+" TEXT,"+
            QUANTITY+" TEXT,"+STOCK+" TEXT,"+TIME_INTERVAL+" TEXT,"+DESCRIPTION+" TEXT"+")";


    //Table for follow up doctor
    private static final String CREATE_MEDIA_ALERT_TABLE3="CREATE TABLE "+DATABASE_TABLE_FOLLOW_UP+"("+FOLLOW_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+DOCTOR_NAME+" TEXT,"+FOLLOW_DATE+" TEXT,"+FOLLOW_TIME+" TEXT,"+DOCTOR_LOCATION+" TEXT,"+
            DISEASE_NAME+" TEXT,"+DESCRIPTION_FOLLOW+" TEXT"+")";

    //Table for sms
    private static final String[] COLUMNS5={SMS_ID,MEDICINE_NAME,SMS_DATE,SMS_TIME,SMS_MOBILE,SMS_DESCRIPTION};
    private static final String CREATE_MEDIA_ALERT_TABLE4="CREATE TABLE "+SMS_TABLE+"("+SMS_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+SMS_MEDICINE+" TEXT,"+SMS_DATE+" TEXT,"+SMS_TIME+" TEXT,"+SMS_MOBILE+" TEXT,"+
            SMS_DESCRIPTION+" TEXT"+")";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MEDIA_ALERT_TABLE);
        db.execSQL(CREATE_MEDIA_ALERT_TABLE2);
        db.execSQL(CREATE_MEDIA_ALERT_TABLE3);
        db.execSQL(CREATE_MEDIA_ALERT_TABLE4);

    }

    public void createSms(Media media){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values2=new ContentValues();
        values2.put(SMS_MEDICINE,media.getMedicineName());
        values2.put(SMS_DATE,media.getDate());
        values2.put(SMS_TIME,media.getTime());
        values2.put(SMS_MOBILE,media.getMobile());
        values2.put(SMS_DESCRIPTION,media.getDescription());

        db.insert(SMS_TABLE, null, values2);
        db.close();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
db.execSQL("DROP TABLE IF EXISTS media_store");
        this.onCreate(db);
    }

    public void updatetable(Media media,int pos){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(MEDICINE_NAME,media.getMedicineName());
        values.put(DATE, media.getDate());
        values.put(STOCK,media.getStock());
        values.put(TIME, media.getTime());
        values.put(QUANTITY,media.getQuantity());
        values.put(DOSAGE, media.getDosage());
        values.put(TIME_INTERVAL,media.getInterval());
        values.put(DESCRIPTION,media.getDescription());
        db.update(DATABASE_TABLE,values,"id"+"="+pos,null);
        db.update(DATABASE_TABLE2,values,"id"+"="+pos,null);
    }

    public void createMedia(Media media){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(MEDICINE_NAME,media.getMedicineName());
        values.put(DATE, media.getDate());
        values.put(STOCK,media.getStock());
        values.put(TIME, media.getTime());
        values.put(QUANTITY,media.getQuantity());
        values.put(DOSAGE, media.getDosage());
        values.put(TIME_INTERVAL,media.getInterval());
        values.put(DESCRIPTION,media.getDescription());
        Log.d("Sabuj", media.getDescription());

        ContentValues values1=new ContentValues();
        values1.put(MEDICINE_NAME,media.getMedicineName());
        values1.put(DATE, media.getDate());
        values1.put(STOCK,media.getStock());
        values1.put(TIME, media.getTime());
        values1.put(QUANTITY,media.getQuantity());
        values1.put(DOSAGE, media.getDosage());
        values1.put(TIME_INTERVAL,media.getInterval());
        values1.put(DESCRIPTION,media.getDescription());
        Log.d("Sabuj", media.getDescription());
        db.insert(DATABASE_TABLE, null, values);

        db.insert(DATABASE_TABLE2, null, values1);
        db.close();

    }

    public void createMediaForFollow(Media media){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DOCTOR_NAME,media.getDoctorname());
        values.put(FOLLOW_DATE, media.getDate());

        values.put(FOLLOW_TIME, media.getTime());
        values.put(DOCTOR_LOCATION,media.getLocation());
        values.put(DISEASE_NAME, media.getDisease());

        values.put(DESCRIPTION_FOLLOW,media.getDescription());

        db.insert(DATABASE_TABLE_FOLLOW_UP,null,values);

    }


    public Media  showData(){

        String query="SELECT * FROM "+DATABASE_TABLE+" WHERE id>0";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(query, null);
        Media media=new Media();
        if (cursor.moveToFirst()) {
//            cursor.moveToFirst();
            do {
                media.setId(Integer.parseInt(cursor.getString(0)));
                media.setMedicineName(cursor.getString(1));
                media.setDate(cursor.getString(2));
                media.setTime(cursor.getString(3));
                media.setDosage(cursor.getString(4));
                media.setQuantity(cursor.getString(5));
                media.setStock(cursor.getString(6));
                media.setInterval(cursor.getString(7));
                media.setDescription(cursor.getString(8));
            } while(cursor.moveToNext());
            cursor.close();
        }
        else{
            media= null;
        }
        db.close();

        return media;

    }
}
