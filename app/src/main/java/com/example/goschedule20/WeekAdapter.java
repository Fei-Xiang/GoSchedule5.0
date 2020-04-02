package com.example.goschedule20;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class WeekAdapter extends ArrayAdapter<Week> {

    Activity context;
    int resource;
    List<Week> objects;
    public WeekAdapter(Activity context, int resource, List<Week> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource=resource;
        this.objects=objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource,null);

        TextView txtDay = (TextView) row.findViewById(R.id.title);
        TextView txtType = (TextView) row.findViewById(R.id.author);
        TextView txtAvailable = (TextView) row.findViewById(R.id.price);
        ImageView btnLike = (ImageView) row.findViewById(R.id.btnLike);
        /** Set data to row*/
        final Week week = this.objects.get(position);
        txtDay.setText(week.getDay());
        txtType.setText(week.getType());
        txtAvailable.setText(week.getAvailable());

        /**Set Event Onclick*/
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage(week);
            }
        });
        return row;
    }

    private void showMessage(Week week) {
        Toast.makeText(this.context,week.toString(),Toast.LENGTH_LONG).show();
    }
}
