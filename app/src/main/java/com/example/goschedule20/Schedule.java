package com.example.goschedule20;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.model.value.IntegerValue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.List;
import java.util.Locale;

public class Schedule extends Fragment {

    TextView displayCurrentDate;
    Calendar currentDate = Calendar.getInstance();
    ImageButton previousDate, nextDate;
    RelativeLayout layout;
    DatabaseReference reference;
    DatabaseReference shifts;
    String lastName, position, date, start, end;
    List<Shift> dailyShift = new ArrayList<>();
    int event = 0, left=0;
    int eventIndex;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schedule, container, false);

        displayCurrentDate = view.findViewById(R.id.current_date);
        previousDate = view.findViewById(R.id.previous);
        nextDate = view.findViewById(R.id.next);
        layout = view.findViewById(R.id.relative);
        reference = FirebaseDatabase.getInstance().getReference();
        shifts = reference.child("Shifts");
        eventIndex = layout.getChildCount();

        //Display current date
        displayCurrentDate.setText(dateFormat(currentDate.getTime()));

        //Move to the next or previous day
        previousDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPrevious();
            }
        });
        nextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNext();
            }
        });
        return view;
    }


    // retrieves the data from data base
    @Override
    public void onStart() {
        super.onStart();

        shifts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Get the current date and set it in calDate for comparison in the next step
                Calendar calDate = Calendar.getInstance();
                calDate.setTime(currentDate.getTime());

                Calendar dDate = Calendar.getInstance();
                //Get day, month, and year of current date, and store it as integer
                int calDay = calDate.get(Calendar.DAY_OF_MONTH);
                int calMonth = calDate.get(Calendar.MONTH);
                int calYear = calDate.get(Calendar.YEAR);

                //Retrieves the data from the Firebase until none
                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    // get the data and store it in variables
                    lastName = ds.child("name").getValue(String.class);
                    position = ds.child("position").getValue(String.class);
                    date = ds.child("day").getValue(String.class);
                    start = ds.child("startTime").getValue(String.class);
                    end = ds.child("endTime").getValue(String.class);

                    // Get work date from firebase and store it in dDate
                    Date reminderDate = convertToDate(date);
                    dDate.setTime(reminderDate);

                    //Start and end are in String form, so call function convertToDate to convert it in Date and set the date in dDate
                    int dDay = dDate.get(Calendar.DAY_OF_MONTH);
                    int dMonth = dDate.get(Calendar.MONTH);
                    int dYear = dDate.get(Calendar.YEAR);

                    //If dDate(date from firebase) is the same date as current date, then add to dailyEmp object
                    if (calDay == dDay && calMonth == dMonth && calYear == dYear) {
                         dailyShift.add(new Shift(lastName, position, start, end));
                    }
                }
                //For creating block height
                 try {
                     getDailyEmployee();
                 }catch (Exception e){
                    e.printStackTrace();
                     Log.e("", " Error:");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void setPrevious(){
        layout.removeViewAt(eventIndex-1);
        currentDate.add(Calendar.DAY_OF_MONTH, -1);
        displayCurrentDate.setText(dateFormat(currentDate.getTime()));
        onStart();
    }

    private void setNext(){
        layout.removeViewAt(eventIndex-1);
        currentDate.add(Calendar.DAY_OF_MONTH, 1);
        displayCurrentDate.setText(dateFormat(currentDate.getTime()));
        onStart();
    }


    private String dateFormat(Date date){
        //Convert the Date form to String form in order to display it in TextView
        String dateString;
        return dateString = new SimpleDateFormat("EEEE, d MMMM yyyy", Locale.CANADA).format(date);
    }

    private Date convertToDate(String stringDate){
        //Convert String form to Date form in order to calculate time
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.CANADA);
        Date date = null;

        try{
            date = format.parse(stringDate);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return date;
    }

    public void getDailyEmployee(){
        //Create the block height of each employee work hours on that day
        for(Shift object: dailyShift){

            String startTime = object.getStartTime();
            String endTime = object.getEndTime();

            String empName = object.getName();
            String empPosition = object.getPosition();

            //Call getTimeDifferent for calculate the duration, and return it to block height
            int blockHeight = getTimeDifferent(startTime, endTime);
            //Call getTimeSection to get the start of block hour
            getTimeSection(startTime, blockHeight, empName, empPosition);

        }
        //set the event on that day to 0, after create all event views
        event=0;
        left=0;
    }

    private int getTimeDifferent(String start, String end){

        //GetTime will return the value in long, and convert it to minutes by (x/1000)/60
        java.text.DateFormat df = new java.text.SimpleDateFormat("hh:mm");
        Date date1 = null;
        try {
            date1 = df.parse(start);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date2 = null;
        try {
            date2 = df.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long timeDifference = date2.getTime() - date1.getTime();
        Calendar mCal = Calendar.getInstance();
        mCal.setTimeInMillis(timeDifference);

        //Return time as integer
        int timeDifferenceInt = (int)((timeDifference/1000)/60);
        return timeDifferenceInt;
    }

    private void getTimeSection(String date, int height, String lastName, String position){

        //First format the current date into String form
        String value = date;

        //Split the hour and minute, and convert hour to minutes to find total work hour and the result value is the start block position
        String[]totalMin = value.split(":");
        int hour = Integer.parseInt(totalMin[0]);
        int mins = Integer.parseInt(totalMin[1]);
        int topViewMargin = (hour * 60) + mins;

        //Pass all necessary info to create the block hour
        createView(topViewMargin, height, lastName, position);
    }

    private void createView(int margin, int height, String name, String position) {

        //First declare TextView for holding the information that will be presented eg. name, position
        TextView mEventView = new TextView(getActivity().getBaseContext());
        //Create relative layout to display the block hour
        RelativeLayout.LayoutParams lParam = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        //Creating the top block using margin and get the mobile size by calling getDisplayMetrics().density, then the block will show at the same spot regarding to the size of devices.
        lParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lParam.topMargin = (int)(margin*Resources.getSystem().getDisplayMetrics().density);


        //Set left margin to make a space between time section, for many events
        if(event/2==0){
            left += 25;
        }
        else if(event/2!=0){
            left += 200;
        }

        lParam.leftMargin = left;
        mEventView.setLayoutParams(lParam);
        mEventView.setPadding(25, 0, 25, 0);

        //Set height of the block using total work hours, and set the width of the block
        mEventView.setHeight((int)(height*Resources.getSystem().getDisplayMetrics().density));
        mEventView.setWidth(150);
        mEventView.setGravity(0x11);

        //Decoration the block and display text inside.
        mEventView.setTextColor(Color.parseColor("#ffffff"));
        mEventView.setText(name+" (" + position + " )");
        mEventView.setBackgroundColor(Color.parseColor("#00574B"));
        layout.addView(mEventView, eventIndex -1);
        event++;
    }
}
