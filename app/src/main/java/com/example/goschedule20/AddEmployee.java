package com.example.goschedule20;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddEmployee extends Fragment {

    EditText fullName;
    EditText phoneNumber;
    EditText email;
    Spinner position;

    CheckBox monday,tuesday,wednesday,thursday,friday,saturday,sunday;
    Spinner startTimeMonday,endTimeMonday;
    Spinner startTimeTuesday,endTimeTuesday;
    Spinner startTimeWednesday,endTimeWednesday;
    Spinner startTimeThursday,endTimeThursday;
    Spinner startTimeFriday,endTimeFriday;
    Spinner startTimeSaturday,endTimeSaturday;
    Spinner startTimeSunday,endTimeSunday;

    Button submit;

    DatabaseReference reff;
    Employee employee;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_employee, container, false);

        fullName = view.findViewById(R.id.employeeName);
        phoneNumber = view.findViewById(R.id.phoneNo);
        email = view.findViewById(R.id.email);
        position = view.findViewById(R.id.position);

        monday = view.findViewById(R.id.mondayAvailability);
        tuesday = view.findViewById(R.id.tuesdayAvailability);
        wednesday = view.findViewById(R.id.wednesdayAvailability);
        thursday = view.findViewById(R.id.thursdayAvailability);
        friday = view.findViewById(R.id.fridayAvailability);
        saturday = view.findViewById(R.id.saturdayAvailability);
        sunday = view.findViewById(R.id.sundayAvailability);

        monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(monday.isChecked())
                {
                    startTimeMonday.setEnabled(true);
                    endTimeMonday.setEnabled(true);
                }
                else {
                    startTimeMonday.setEnabled(false);
                    endTimeMonday.setEnabled(false);
                }
            }
        });

        tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tuesday.isChecked()) {
                    startTimeTuesday.setEnabled(true);
                    endTimeTuesday.setEnabled(true);
                } else {
                    startTimeTuesday.setEnabled(false);
                    endTimeTuesday.setEnabled(false);
                }
            }
        });
        wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wednesday.isChecked())
                {
                    startTimeWednesday.setEnabled(true);
                    endTimeWednesday.setEnabled(true);
                }
                else {
                    startTimeWednesday.setEnabled(false);
                    endTimeWednesday.setEnabled(false);
                }
            }
        });
        thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thursday.isChecked())
                {
                    startTimeThursday.setEnabled(true);
                    endTimeThursday.setEnabled(true);
                }
                else {
                    startTimeThursday.setEnabled(false);
                    endTimeThursday.setEnabled(false);
                }
            }
        });
        friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(friday.isChecked())
                {
                    startTimeFriday.setEnabled(true);
                    endTimeFriday.setEnabled(true);
                }
                else {
                    startTimeFriday.setEnabled(false);
                    endTimeFriday.setEnabled(false);
                }
            }
        });
        saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(saturday.isChecked())
                {
                    startTimeSaturday.setEnabled(true);
                    endTimeSaturday.setEnabled(true);
                }
                else {
                    startTimeSaturday.setEnabled(false);
                    endTimeSaturday.setEnabled(false);
                }
            }
        });
        sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sunday.isChecked())
                {
                    startTimeSunday.setEnabled(true);
                    endTimeSunday.setEnabled(true);
                }
                else {
                    startTimeSunday.setEnabled(false);
                    endTimeSunday.setEnabled(false);
                }
            }
        });
        //Connects the spinners of each day and disables them
        startTimeMonday = view.findViewById(R.id.startTimeMonday);
        startTimeMonday.setEnabled(false);
        endTimeMonday = view.findViewById(R.id.endTimeMonday);
        endTimeMonday.setEnabled(false);

        startTimeTuesday = view.findViewById(R.id.startTimeTuesday);
        startTimeTuesday.setEnabled(false);
        endTimeTuesday = view.findViewById(R.id.endTimeTuesday);
        endTimeTuesday.setEnabled(false);

        startTimeWednesday = view.findViewById(R.id.startTimeWednesday);
        startTimeWednesday.setEnabled(false);
        endTimeWednesday = view.findViewById(R.id.endTimeWednesday);
        endTimeWednesday.setEnabled(false);

        startTimeThursday = view.findViewById(R.id.startTimeThursday);
        startTimeThursday.setEnabled(false);
        endTimeThursday = view.findViewById(R.id.endTimeThursday);
        endTimeThursday.setEnabled(false);

        startTimeFriday = view.findViewById(R.id.startTimeFriday);
        startTimeFriday.setEnabled(false);
        endTimeFriday = view.findViewById(R.id.endTimeFriday);
        endTimeFriday.setEnabled(false);

        startTimeSaturday = view.findViewById(R.id.startTimeSaturday);
        startTimeSaturday.setEnabled(false);
        endTimeSaturday = view.findViewById(R.id.endTimeSaturday);
        endTimeSaturday.setEnabled(false);

        startTimeSunday = view.findViewById(R.id.startTimeSunday);
        startTimeSunday.setEnabled(false);
        endTimeSunday = view.findViewById(R.id.endTimeSunday);
        endTimeSunday.setEnabled(false);

        //End of Connection

        submit = view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mondayAvailability;
                String tuesdayAvailability;
                String wednesdayAvailability;
                String thursdayAvailability;
                String fridayAvailability;
                String saturdayAvailability;
                String sundayAvailability;

                if(monday.isChecked()){
                    mondayAvailability = startTimeMonday.getSelectedItem().toString() + " - " + endTimeMonday.getSelectedItem().toString();
                }
                else
                {
                    mondayAvailability = "Not Available";
                }
                if(tuesday.isChecked()){
                    tuesdayAvailability = startTimeTuesday.getSelectedItem().toString() + " - " + endTimeTuesday.getSelectedItem().toString();
                }
                else
                {
                    tuesdayAvailability = "Not Available";
                }
                if(wednesday.isChecked()){
                    wednesdayAvailability = startTimeWednesday.getSelectedItem().toString() + " - " + endTimeWednesday.getSelectedItem().toString();
                }
                else
                {
                    wednesdayAvailability = "Not Available";
                }
                if(thursday.isChecked()){
                    thursdayAvailability = startTimeThursday.getSelectedItem().toString() + " - " + endTimeThursday.getSelectedItem().toString();
                }
                else
                {
                    thursdayAvailability = "Not Available";
                }
                if(friday.isChecked()){
                    fridayAvailability = startTimeFriday.getSelectedItem().toString() + " - " + endTimeFriday.getSelectedItem().toString();
                }
                else
                {
                    fridayAvailability = "Not Available";
                }
                if(saturday.isChecked()){
                    saturdayAvailability = startTimeSaturday.getSelectedItem().toString() + " - " + endTimeSaturday.getSelectedItem().toString();
                }
                else
                {
                    saturdayAvailability = "Not Available";
                }
                if(sunday.isChecked()){
                    sundayAvailability = startTimeSunday.getSelectedItem().toString() + " - " + endTimeSunday.getSelectedItem().toString();
                }
                else
                {
                    sundayAvailability = "Not Available";
                }

                employee.setFullName(fullName.getText().toString().trim());
                employee.setEmail(email.getText().toString().trim());
                employee.setPhoneNo(Float.parseFloat(phoneNumber.getText().toString()));
                employee.setPosition(position.getSelectedItem().toString()) ;
                employee.setMondayAvailability(mondayAvailability);
                employee.setTuesdayAvailability(tuesdayAvailability);
                employee.setWednesdayAvailability(wednesdayAvailability);
                employee.setThursdayAvailability(thursdayAvailability);
                employee.setFridayAvailability(fridayAvailability);
                employee.setSaturdayAvailability(saturdayAvailability);
                employee.setSundayAvailability(sundayAvailability);

                reff.child(fullName.getText().toString()).setValue(employee);
                Toast.makeText(getActivity().getBaseContext(),"Data is been saved successfully",Toast.LENGTH_LONG).show();
            }
        });
        employee = new Employee();

        reff = FirebaseDatabase.getInstance().getReference().child("Employee");
       Toast.makeText(getActivity().getBaseContext(),"FireBase connection successful",Toast.LENGTH_LONG).show();
        return view;
    }
}
