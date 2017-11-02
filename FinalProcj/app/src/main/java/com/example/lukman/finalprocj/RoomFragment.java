package com.example.lukman.finalprocj;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.SharedPreferencesCompat;
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
    String roombed = "";
    String [] type = {"-Select Type-","VIP", "Economy"};
    String [] bed = {"-Select Bed-","Single Bed", "Double Bed"};
    String TAG = "Fragment Room";
    String datein, dateout;
    ArrayAdapter<String> typeroom, bedroom;
    Spinner spinnertype, spinnerbed;
    int jumlah=0, qty = 0, cost, total,costnew, total_bayar, totalhari, totalcost2,qtyday2,qtyrooms;
    Button min, plus;
    TextView qtyday, qtybed , DisplayDateIn, DisplayDateOut,qtyroom, costroom ,totalcost, totalall;
    DatePickerDialog.OnDateSetListener DateListenerOut,DateListenerIn;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_room, container, false);
        qtyday = (TextView) v.findViewById(R.id.txtbetween);
        qtyroom =(TextView) v.findViewById(R.id.txtqtyrm);
        costroom = (TextView) v.findViewById(R.id.txtcost);
        totalcost = (TextView) v.findViewById(R.id.txttotalcost);
        plus = (Button) v.findViewById(R.id.plsqty);
        plus.setOnClickListener(new klik_plus());
        min = (Button) v.findViewById(R.id.minusqty);
        min.setOnClickListener(new klik_minus());
        spinnertype = (Spinner) v.findViewById(R.id.spinnertype);
        typeroom = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, type);
        typeroom.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnertype.setAdapter(typeroom);

        spinnerbed = (Spinner) v.findViewById(R.id.spinnerbed);
        bedroom = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, bed);
        bedroom.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerbed.setAdapter(bedroom);

        qtybed = (TextView) v.findViewById(R.id.hslqtyroom);

        roomtype = spinnertype.getSelectedItem().toString();
        roombed = spinnerbed.getSelectedItem().toString();
        changeBed(roomtype, roombed);

        spinnertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                roomtype = parentView.getItemAtPosition(position).toString();
                changeBed(roomtype, roombed);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        spinnerbed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                roombed = parentView.getItemAtPosition(position).toString();
                changeBed(roomtype, roombed);
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
                    /*if (DisplayDateIn == null){
                        Toast.makeText(getActivity(),"Take Your Date In First",Toast.LENGTH_LONG).show();
                    }
                    else{*/
                        /**//*dialogout.getDatePicker().setMinDate(a.getTimeInMillis()+ Integer.parseInt(String.valueOf(DisplayDateIn)));*/


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
                    /*}*/                }
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
                } else {*/
                    if(!DisplayDateIn.getText().toString().matches("")){
                        month = month + 1;
                        dateout = year + "/" + month + "/" + day; //day + "/" + month + "/" + year;
                        Log.d("Date Out", dateout);
                        DisplayDateOut.setText(dateout);
                        UpdateNight();
                    }
                    else {
                        Toast.makeText(getActivity(), "Take Your Date In First", Toast.LENGTH_LONG).show();
                    }
                /*}*/
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
                hitungTotal();
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
                hitungTotal();
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
    public void changeBed(String roomtype, String roombed)
    {
        if (roomtype.equals("Economy") && roombed.equals("Single Bed")){
            qty = 10;
            qtybed.setText(Integer.toString(qty));
            cost = 250000;
            costroom.setText(Integer.toString(cost));
            hitungTotal();

        }
        else if (roomtype.equals("Economy") && roombed.equals("Double Bed")){
            qty=25;
            qtybed.setText(Integer.toString(qty));
            cost = 300000;
            costroom.setText(Integer.toString(cost));
            hitungTotal();
        }
        else if (roomtype.equals("VIP") && roombed.equals("Single Bed")){
            qty=10;
            qtybed.setText(Integer.toString(qty));
            cost = 450000;
            costroom.setText(Integer.toString(cost));
            hitungTotal();
        }
        else if (roomtype.equals("VIP") && roombed.equals("Double Bed")){
            qty = 15;
            qtybed.setText(Integer.toString(qty));
            cost = 500000;
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
    public void hitungTotal(){
        if(qtyday.getText().toString().matches("")){
            totalcost.setText("0");
        }
        else {
            qtyday2 = Integer.parseInt(qtyday.getText().toString());
            qtyrooms = Integer.parseInt(qtyroom.getText().toString());
            costnew = Integer.parseInt(costroom.getText().toString());
            total = costnew * qtyrooms * qtyday2;
            totalcost.setText(Integer.toString(total));
        }
    }
}
