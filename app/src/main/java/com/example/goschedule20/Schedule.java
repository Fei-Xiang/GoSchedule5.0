package com.example.goschedule20;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Schedule extends Fragment {

    String date1 = "31-01-1998 22:00";
    String date2 = "31-01-1998 23:30";
    TextView displayCurrentDate;
    Calendar currentDate = Calendar.getInstance();
    ImageButton previousDate, nextDate;

    RelativeLayout layout;

    int eventIndex;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schedule, container, false);

        displayCurrentDate = view.findViewById(R.id.current_date);
        previousDate = view.findViewById(R.id.previous);
        nextDate = view.findViewById(R.id.next);

        layout = view.findViewById(R.id.relative);
        eventIndex = layout.getChildCount();
        Date value1 = getDate(date1);
        Date value2 = getDate(date2);

        displayCurrentDate.setText(dateFormat(currentDate.getTime()));

        int blockHeight = getTimeDifferent(value1,value2);

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm",  Locale.CANADA);
        String displayValue1 = formatter.format(value1);
        //String displayValue2 = formatter.format(value2);

        String[]totalMin = displayValue1.split(":");
        int hour = Integer.parseInt(totalMin[0]);
        int mins = Integer.parseInt(totalMin[1]);
        int topViewMargin = (hour * 60) + mins;

        createView(topViewMargin, blockHeight);

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

    private void setPrevious(){
        currentDate.add(Calendar.DAY_OF_MONTH, -1);
        displayCurrentDate.setText(dateFormat(currentDate.getTime()));
    }

    private void setNext(){
        currentDate.add(Calendar.DAY_OF_MONTH, 1);
        displayCurrentDate.setText(dateFormat(currentDate.getTime()));
    }

    private String dateFormat(Date date){
        String dateString;
        return dateString = new SimpleDateFormat("EEEE, d MMMM yyyy", Locale.CANADA).format(date);
    }

    private int getTimeDifferent(Date start, Date end){
        long timeDifference = end.getTime() - start.getTime();
        Calendar mCal = Calendar.getInstance();
        mCal.setTimeInMillis(timeDifference);

        return (int)((timeDifference/1000)/60);
    }

    public Date getDate(String dateInString){
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.CANADA);
        Date date = null;
        try {
            date = format.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private void createView(int margin, int height) {
        TextView mEventView = new TextView(getActivity().getBaseContext());
        RelativeLayout.LayoutParams lParam = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        lParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lParam.topMargin = (int)(margin* Resources.getSystem().getDisplayMetrics().density);
        lParam.leftMargin = 25;
        mEventView.setLayoutParams(lParam);
        mEventView.setPadding(25, 0, 25, 0);

        mEventView.setHeight((int)(height*Resources.getSystem().getDisplayMetrics().density));
        mEventView.setWidth(150);
        mEventView.setGravity(0x11);

        mEventView.setTextColor(Color.parseColor("#ffffff"));
        mEventView.setText("Nuke");
        mEventView.setBackgroundColor(Color.parseColor("#000000"));
        layout.addView(mEventView);
    }
}
