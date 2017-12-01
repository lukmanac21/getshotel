package com.example.lukman.finalprocj;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.SharedPreferencesCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.*;

import java.sql.Array;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RoomFragment extends Fragment {
    String roomtype = "";
    String[] type = {"-Select Type-","VIP", "Economy"};
    String TAG = "Fragment Room";
    String datein, dateout;
    ArrayAdapter<String> typeroom;
    View rootView;
    Spinner spinnertype, spinnerbed;
    private int jumlah=0, qty = 0, cost, total,costnew, total_bayar, totalhari, totalcost2,qtyday2,qtyrooms;
    Button min, plus, pesan;
    TextView qtyday, qtybed , DisplayDateIn, DisplayDateOut,qtyroom, costroom ,totalcost, totalall;
    DatePickerDialog.OnDateSetListener DateListenerOut,DateListenerIn;
    private HelperTransaksi dbHelper;
    private SQLiteDatabase mDb;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        dbHelper = new HelperTransaksi(getActivity());
        mDb = dbHelper.getWritableDatabase();

        View v = inflater.inflate(R.layout.fragment_room, container, false);
        qtyday = (TextView) v.findViewById(R.id.txtbetween);
        qtyroom =(TextView) v.findViewById(R.id.txtqtyrm);
        costroom = (TextView) v.findViewById(R.id.txtcost);
        totalcost = (TextView) v.findViewById(R.id.txttotalcost);
        rootView = (View) v.findViewById(R.id.rootView);
        plus = (Button) v.findViewById(R.id.plsqty);
        plus.setSelected(true);
        plus.setFocusable(false);
        plus.setKeyListener(null);
        plus.setOnClickListener(new klik_plus());
        min = (Button) v.findViewById(R.id.minusqty);
        min.setSelected(true);
        min.setFocusable(false);
        min.setKeyListener(null);
        min.setOnClickListener(new klik_minus());
        spinnertype = (Spinner) v.findViewById(R.id.spinnertype);
        typeroom = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, type);
        typeroom.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnertype.setAdapter(typeroom);
        qtybed = (TextView) v.findViewById(R.id.hslqtyroom);

        roomtype = spinnertype.getSelectedItem().toString();
        changeBed(roomtype);
        pesan = (Button)v.findViewById(R.id.btnpesan);
        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });


        spinnertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                roomtype = parentView.getItemAtPosition(position).toString();
                changeBed(roomtype);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        DisplayDateIn = (TextView)v.findViewById(R.id.etdatein);
        DisplayDateOut= (TextView)v.findViewById(R.id.etdateout);
        DisplayDateIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialogin = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        DateListenerIn,
                        year,month,day
                );
                dialogin.getDatePicker().setMinDate(c.getTimeInMillis());
                dialogin.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogin.show();
            }
        });
        DisplayDateOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!DisplayDateIn.getText().toString().matches("")) {

                    final Calendar a = Calendar.getInstance();
                    int year = a.get(Calendar.YEAR);
                    int month = a.get(Calendar.MONTH);
                    int day = a.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog dialogout = new DatePickerDialog(
                            getActivity(),
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            DateListenerOut,
                            year, month, day
                    );
                    Calendar date;
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                        a.setTime(formatter.parse(DisplayDateIn.getText().toString()));
                        Log.d("Date Out Min", a.toString());
                    }
                    catch (Exception e) {
                        //
                    }
                    dialogout.getDatePicker().setMinDate(a.getTimeInMillis());
                    dialogout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogout.show();
                }
                else {
                    Toast.makeText(getActivity(), "Take Your Date In First", Toast.LENGTH_LONG).show();
                }
            }
        });
        DateListenerIn = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month= month+1;
                datein = year + "/" + month + "/" + day; //day + "/" + month + "/" + year;
                Log.d("Date In", datein);
                DisplayDateIn.setText(datein);
                    hitungTotal();
            }
        };
        DateListenerOut = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                if(!DisplayDateIn.getText().toString().matches("")){
                    month = month + 1;
                    dateout = year + "/" + month + "/" + day; //day + "/" + month + "/" + year;
                    Log.d("Date Out", dateout);
                    DisplayDateOut.setText(dateout);
                    String QtyNight = QtyDay(createDateString(DisplayDateIn.getText().toString()), createDateString(DisplayDateOut.getText().toString()));
                    qtyday.setText(QtyNight);
                        hitungTotal();
                }
                else {
                    Toast.makeText(getActivity(), "Take Your Date In First", Toast.LENGTH_LONG).show();
                }
            }
        };
        return v;
    }
    private class klik_plus implements View.OnClickListener{

        public void onClick(View v){
            if  (jumlah == qty) {
                Toast.makeText(getActivity(),"Room Maksimal", Toast.LENGTH_LONG).show();
            }
            else{
                jumlah++;
                if (qtyday.getText().toString().matches("") || jumlah == 0 || costroom.getText().toString().matches("")) {
                    totalcost.setText("0");
                } else {
                    qtyday2 = Integer.parseInt(qtyday.getText().toString());
                    costnew = Integer.parseInt(costroom.getText().toString());
                    total = costnew * jumlah * qtyday2;
                    totalcost.setText(Integer.toString(total));
                }


            }
            qtyroom.setText(Integer.toString(jumlah));
        }
    }
    private class klik_minus implements View.OnClickListener{
        public void onClick(View v){
            if (jumlah <= 0){
                jumlah = 0;
            }else {
                jumlah--;
                if (qtyday.getText().toString().matches("") || jumlah == 0 || costroom.getText().toString().matches("")) {
                    totalcost.setText("0");
                } else {
                    qtyday2 = Integer.parseInt(qtyday.getText().toString());
                    costnew = Integer.parseInt(costroom.getText().toString());
                    total = costnew * jumlah * qtyday2;
                    totalcost.setText(Integer.toString(total));
                }
            }
            qtyroom.setText(Integer.toString(jumlah));

        }

    }
    public String QtyDay(Date in, Date out) {
        long timeIn = in.getTime();
        long timeOut = out.getTime();
        long oneday = 60 * 60 * 24 * 1000;
        long delta = (timeOut - timeIn)/ oneday;
        if (delta > 0 ) {
            return String.valueOf(delta);
        } else {
            return "0";
        }
    }
    private Date createDateString(String dateString){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            date = format.parse(dateString);
            System.out.println(date);
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        return date;
    }
    public void changeBed(String roomtype)
    {
        if (roomtype.equals("Economy")){
            qty = 10;
            qtybed.setText(Integer.toString(qty));
            cost = 250000;
            costroom.setText(Integer.toString(cost));
                hitungTotal();

        }
        else if (roomtype.equals("VIP")){
            qty=10;
            qtybed.setText(Integer.toString(qty));
            cost = 450000;
            costroom.setText(Integer.toString(cost));
                hitungTotal();
        }
     }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Get Room");
    };

    public static Calendar DateToCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
    public void hitungTotal() {
        if (qtyday.getText().toString().matches("") || jumlah == 0 || costroom.getText().toString().matches("")) {
            totalcost.setText("0");
        } else {

            qtyday2 = Integer.parseInt(qtyday.getText().toString());
            qtyrooms = Integer.parseInt(qtyroom.getText().toString());
            costnew = Integer.parseInt(costroom.getText().toString());
            total = costnew * qtyrooms * qtyday2;
            totalcost.setText(Integer.toString(total));
        }
    }
    public void save(){
        String type = spinnertype.getItemAtPosition(Integer.parseInt(roomtype)).toString();
        String qty = qtyroom.getText().toString();
        String tanggalin = DisplayDateIn.getText().toString();
        String tanggalout = DisplayDateOut.getText().toString();
        String total = totalcost.getText().toString();
        if (!TextUtils.isEmpty(type) && !TextUtils.isEmpty(qty) && !TextUtils.isEmpty(tanggalin) && !TextUtils.isEmpty(tanggalout) && !TextUtils.isEmpty(total)){
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseTransaksi.TransaksiKamar.COLOUMN_TYPE, type);
            contentValues.put(DatabaseTransaksi.TransaksiKamar.COLOUMN_QTYROOM, qty);
            contentValues.put(DatabaseTransaksi.TransaksiKamar.COLOUMN_TANGGALIN, tanggalin);
            contentValues.put(DatabaseTransaksi.TransaksiKamar.COLOUMN_TANGGALOUT, tanggalout);
            contentValues.put(DatabaseTransaksi.TransaksiKamar.COLOUMN_TOTAL, total);

            long result = mDb.insert(
                    DatabaseTransaksi.TransaksiKamar.TABLE_NAME,
                    null,
                    contentValues
            );
            if(result > 0){
                finish();
                return;
            }else {
                Snackbar snackbar = Snackbar.make(
                        rootView,
                        "GAGAL",
                        Snackbar.LENGTH_LONG
                );
                snackbar.show();
            }
        }
        else {
            Snackbar snackbar = Snackbar.make(
                    rootView,
                    "Silahkan isi form terlebih dahulu",
                    Snackbar.LENGTH_LONG
            );
            snackbar.show();
        }

    }

    private void finish() {
        finish();
    }


}