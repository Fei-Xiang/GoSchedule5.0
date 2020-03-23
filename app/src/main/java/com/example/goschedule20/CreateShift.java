package com.example.goschedule20;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CreateShift extends Fragment {

    ArrayList<String> arrayEmployees = new ArrayList<>();
    ArrayAdapter<String> adapterEmployees;

    Spinner date, startTime, endTime, position, employee;
    DatabaseReference reff;
    DatabaseReference employees;
    Shift shift;

    Button submit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_shift, container, false);

        employee = view.findViewById(R.id.employeeList);

        date = view.findViewById(R.id.dateSpinner);
        position = view.findViewById(R.id.position);
        startTime = view.findViewById(R.id.startTime);
        endTime = view.findViewById(R.id.endTime);
        submit = view.findViewById(R.id.submit);

        reff = FirebaseDatabase.getInstance().getReference();
        employees = reff.child("Employee");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String firstName = ds.child("firstName").getValue(String.class);
                    String lastName = ds.child("lastName").getValue(String.class);
                    arrayEmployees.add(firstName + " " + lastName);
                    adapterEmployees.notifyDataSetChanged();
                }
                adapterEmployees = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, arrayEmployees);
                adapterEmployees.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                employee.setAdapter(adapterEmployees);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //shift.setDay(date.getSelectedItem().toString());
                shift.setPosition(position.getSelectedItem().toString());
                shift.setStartTime(startTime.getSelectedItem().toString());
                shift.setEndTime(endTime.getSelectedItem().toString());

                reff.child("shift1").setValue(shift);
                Toast.makeText(getActivity().getBaseContext(),"Data is been saved successfully",Toast.LENGTH_LONG).show();
            }
        });

        shift = new Shift();
        reff = FirebaseDatabase.getInstance().getReference().child("Shifts");
        Toast.makeText(getActivity().getBaseContext(),"FireBase connection successful",Toast.LENGTH_LONG).show();
        return view;
    }
}
