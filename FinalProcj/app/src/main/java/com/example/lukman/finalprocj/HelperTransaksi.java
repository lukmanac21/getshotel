package com.example.lukman.finalprocj;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HelperTransaksi extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "transaksi.db";
    private static final int DATABASE_VERSION = 1;

    public HelperTransaksi(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_TRANSAKSI_TABLE =
                "CREATE TABLE " + DatabaseTransaksi.TransaksiKamar.TABLE_NAME+" (" +
                        DatabaseTransaksi.TransaksiKamar._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        DatabaseTransaksi.TransaksiKamar.COLOUMN_TYPE+ " TEXT NOT NULL,"+
                        DatabaseTransaksi.TransaksiKamar.COLOUMN_QTYROOM+ " TEXT NOT NULL,"+
                        DatabaseTransaksi.TransaksiKamar.COLOUMN_TANGGALIN+ " TEXT NULL,"+
                        DatabaseTransaksi.TransaksiKamar.COLOUMN_TANGGALOUT+ " TEXT NULL,"+
                        DatabaseTransaksi.TransaksiKamar.COLOUMN_TOTAL+ " TEXT NULL"+
                        ")";
        sqLiteDatabase.execSQL(SQL_CREATE_TRANSAKSI_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if( i1 > i){
            sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " +
                    DatabaseTransaksi.TransaksiKamar.TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }
}
