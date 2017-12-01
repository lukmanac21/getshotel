package com.example.lukman.finalprocj;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ProfilFragment extends Fragment {
    RecyclerView view_history;
    ViewItemHolder adapter;
    private SQLiteDatabase mDb;
    private HelperTransaksi dbHelper;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profil, container, false);
        dbHelper = new HelperTransaksi(getActivity());
        mDb = dbHelper.getReadableDatabase();

        view_history = (RecyclerView)v.findViewById(R.id.view_history);
        adapter = new ViewItemHolder(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        view_history.setLayoutManager(linearLayoutManager);
        view_history.setAdapter(adapter);

        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        swapData();
    }

    private void swapData(){
        adapter.swapCursor(getAllHistory());
        adapter.notifyDataSetChanged();
    }

    private Cursor getAllHistory(){
        return mDb.query(
                DatabaseTransaksi.TransaksiKamar.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("History");
    }
}
