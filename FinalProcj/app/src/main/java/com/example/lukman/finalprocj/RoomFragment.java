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
    ArrayAdapter<String> typeroom, bedroom;
    Spinner spinnertype, spinnerbed;
    int jumlah=0, qty = 0, cost, total;
    Button min, plus;
    TextView qtyday, qtybed , DisplayDateIn, DisplayDateOut,qtyroom, costroom ,totalcost;
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
                dialogin.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogin.show();
            }
        });
        DisplayDateOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar a = Calendar.getInstance();
                int year = a.get(Calendar.YEAR);
                int month = a.get(Calendar.MONTH);
                int day = a.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialogout = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        DateListenerOut,
                        year,month,day
                );
                dialogout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogout.show();
            }
        });
        DateListenerIn = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month= month+1;
                Log.d(TAG ,"onDateSet:mm/dd/yyy"+day+"/"+month+"/"+year);
                String date =day+"/"+month+"/"+year;
                DisplayDateIn.setText(date);

            }
        };
        DateListenerOut = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month= month+1;
                Log.d(TAG ,"onDateSet:mm/dd/yyy"+day+"/"+month+"/"+year);
                String date =day+"/"+month+"/"+year;
                DisplayDateOut.setText(date);
                UpdateNight();
            }
        };
        if (costroom != null && qtyroom!= null){
            totalCost();
        }

        return v;
    }
    public void totalCost (){
        total = Integer.parseInt(String.valueOf(qtyroom)) * Integer.parseInt(String.valueOf(costroom));
        totalcost.setText(total);
    }
    private class klik_plus implements View.OnClickListener{

        public void onClick(View v){
            if  (jumlah == qty) {
                Toast.makeText(getActivity(),"Room Maksimal", Toast.LENGTH_LONG).show();
            }
            else{
                jumlah++;
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
            }
            qtyroom.setText(Integer.toString(jumlah));

        }

    }
    public void UpdateNight(){
        try{ String QtyNight = QtyDay(createDateString(DisplayDateIn.getText().toString()),
                createDateString(DisplayDateOut.getText().toString()));
                qtyday.setText(QtyNight + " Days");
                }
        catch (Exception e){
        }

    }
    public String QtyDay(Date in, Date out) {
        long timeIn = in.getTime();
        long timeOut = out.getTime();
        long oneday = 1000 * 60 * 60 * 24;
        long delta = (timeOut - timeIn) / oneday;
        if (delta > 0 ) {
            return String.valueOf(delta);
        } else {
            return "0";
        }
    }
    private Date createDateString(String dateString){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
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
        }
        else if (roomtype.equals("Economy") && roombed.equals("Double Bed")){
            qty=25;
            qtybed.setText(Integer.toString(qty));
            cost = 300000;
            costroom.setText(Integer.toString(cost));
        }
        else if (roomtype.equals("VIP") && roombed.equals("Single Bed")){
            qty=10;
            qtybed.setText(Integer.toString(qty));
            cost = 450000;
            costroom.setText(Integer.toString(cost));
        }
        else if (roomtype.equals("VIP") && roombed.equals("Double Bed")){
            qty = 15;
            qtybed.setText(Integer.toString(qty));
            cost = 500000;
            costroom.setText(Integer.toString(cost));
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Get Room");
    };


}