package com.example.lukman.finalprocj;

import android.provider.BaseColumns;

/**
 * Created by lukman on 01/12/17.
 */

public class DatabaseTransaksi {
    public static final class  TransaksiKamar implements BaseColumns{
        public static final String TABLE_NAME = "transaksi";
        public static final String COLOUMN_TYPE =  "typeroom";
        public static final String COLOUMN_QTYROOM =  "qtyroom";
        public static final String COLOUMN_TANGGALIN =  "tanggalin";
        public static final String COLOUMN_TANGGALOUT =  "tanggalout";
        public static final String COLOUMN_TOTAL =  "total";
    }
}
