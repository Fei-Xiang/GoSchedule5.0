package com.example.goschedule20;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CreateShift extends Fragment {

    Spinner startTime, endTime, position, employee;
    DatabaseReference reff;
    DatabaseReference employees;
    Shift shift;

    TextView date;
    Button datePicker,submit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_shift, container, false);

        employee = view.findViewById(R.id.employeeList);
        date = view.findViewById(R.id.date);
        datePicker = view.findViewById(R.id.datePicker);
        position = view.findViewById(R.id.position);
        startTime = view.findViewById(R.id.startTime);
        endTime = view.findViewById(R.id.endTime);
        submit = view.findViewById(R.id.submit);

        reff = FirebaseDatabase.getInstance().getReference();
        employees = reff.child("Employee");

        reff.child("Employee").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> areas = new ArrayList<String>();

                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                        String name = areaSnapshot.child("fullName").getValue(String.class);
                        areas.add(name);
                }
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                employee.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getFragmentManager(),"date picker");
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shift.setDay(date.getText().toString());
                shift.setPosition(position.getSelectedItem().toString());
                shift.setName(employee.getSelectedItem().toString());
                shift.setStartTime(startTime.getSelectedItem().toString());
                shift.setEndTime(endTime.getSelectedItem().toString());

                reff.child("Shift: " + employee.getSelectedItem().toString()).setValue(shift);
                Toast.makeText(getActivity().getBaseContext(),"Data is been saved successfully",Toast.LENGTH_LONG).show();
            }
        });

        shift = new Shift();
        reff = FirebaseDatabase.getInstance().getReference().child("Shifts");
        Toast.makeText(getActivity().getBaseContext(),"FireBase connection successful",Toast.LENGTH_LONG).show();
        return view;
    }
}
