package com.example.lukman.finalprocj;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


public class TransaksiViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_type,tv_qty,tv_datein,tv_dateout,tv_total;

    public TransaksiViewHolder(View itemView) {
        super(itemView);
        tv_type = (TextView)itemView.findViewById(R.id.tv_type);
        tv_qty = (TextView)itemView.findViewById(R.id.tv_qty);
        tv_datein = (TextView)itemView.findViewById(R.id.tv_datein);
        tv_dateout = (TextView)itemView.findViewById(R.id.tv_dateout);
        tv_total = (TextView)itemView.findViewById(R.id.tv_total);
    }
}