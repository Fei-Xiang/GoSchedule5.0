package com.example.goschedule20;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class WeekActivity extends AppCompatActivity {


    ListView lv;
    ArrayList<Week> List;
    WeekAdapter weekAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvent();
    }
    private void addControls() {

        lv = (ListView) findViewById(R.id.lvBookList);
        List = new ArrayList<>();

        weekAdapter = new WeekAdapter(WeekActivity.this,R.layout.item,List);
        lv.setAdapter(weekAdapter);
    }

    private void addEvent() {
        createData();
    }

    /** Add data to List*/
    public void createData() {
        Week week = new Week("Monday","10.00am - 1.00pm" ,"Working");
        List.add(week);
        week = new Week("Tuesday","10.00am - 1.00pm","Working");
        List.add(week);
        week = new Week("Wednesday","Instore","Not Working");
        List.add(week);
        week = new Week("Thursday","Instore","Not Working");
        List.add(week);
        week = new Week("Friday","10.00am - 1.00pm","Working");
        List.add(week);
        week = new Week("Saturday","2.00pm - 10.00pm","Working");
        List.add(week);
        week = new Week("Sunday","Instore","Not Working");
        List.add(week);
        weekAdapter.notifyDataSetChanged();
    }
}
