package hacking101.a3tsroutes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StopsDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Stops.db";
    public static final String TABLE_NAME = "stops_table";
    public static final String COL_1 = "STOP_ID";
    public static final String COL_2 = "ADDRESS";
    public static final String COL_3 = "LATITUDE";
    public static final String COL_4 = "LONGITUDE";
    public static final String COL_5 = "LOCATION";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (STOP_ID INTEGER PRIMARY KEY,ADDRESS TEXT,LATITUDE TEXT,LONGITUDE TEXT, LOCATION TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public StopsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

}
