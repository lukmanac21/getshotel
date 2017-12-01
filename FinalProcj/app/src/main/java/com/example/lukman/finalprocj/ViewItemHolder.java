package com.example.lukman.finalprocj;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ViewItemHolder  extends RecyclerView.Adapter<TransaksiViewHolder>  {
    Context mContext;
    Cursor mCursor;

    public ViewItemHolder(Context context){
        this.mContext=context;
    }

    public void swapCursor(Cursor cursor){
        this.mCursor=cursor;
    }

    @Override
    public TransaksiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.itemview,parent,false);
        return new TransaksiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransaksiViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        int typeColoumnIndex = mCursor.getColumnIndex(DatabaseTransaksi.TransaksiKamar.COLOUMN_TYPE);
        int qtyColoumnIndex = mCursor.getColumnIndex(DatabaseTransaksi.TransaksiKamar.COLOUMN_QTYROOM);
        int dateinColoumnIndex = mCursor.getColumnIndex(DatabaseTransaksi.TransaksiKamar.COLOUMN_TANGGALOUT);
        int dateoutColoumnIndex = mCursor.getColumnIndex(DatabaseTransaksi.TransaksiKamar.COLOUMN_TANGGALIN);
        int totalColoumnIndex = mCursor.getColumnIndex(DatabaseTransaksi.TransaksiKamar.COLOUMN_TOTAL);

        String type = mCursor.getString(typeColoumnIndex);
        String qty = mCursor.getString(qtyColoumnIndex);
        String datein = mCursor.getString(dateinColoumnIndex);
        String dateout = mCursor.getString(dateoutColoumnIndex);
        String total = mCursor.getString(totalColoumnIndex);

        holder.tv_type.setText(type);
        holder.tv_qty.setText(qty);
        holder.tv_datein.setText(datein);
        holder.tv_dateout.setText(dateout);
        holder.tv_total.setText(total);
    }

    @Override
    public int getItemCount() {
        if(mCursor == null){
            return 0;
        }
        return mCursor.getCount();
    }
}
