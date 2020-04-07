package com.example.goschedule20;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SeeContact extends Fragment {
    TextView employeeName, phone, position, email;
    TextView monday,tuesday,wednesday,thursday,friday,saturday,sunday;
    DatabaseReference reff;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.see_contact, container, false);

        String employee = null;

        Bundle bundle = getArguments();
        if(bundle != null) {
            employee = bundle.getString("EmployeeName");
        }

        employeeName = view.findViewById(R.id.employeeName);
        phone = view.findViewById(R.id.phoneNo);
        position = view.findViewById(R.id.position);
        email = view.findViewById(R.id.email);

        monday = view.findViewById(R.id.monday_Times);
        tuesday = view.findViewById(R.id.tuesday_Times);
        wednesday = view.findViewById(R.id.wednesday_Times);
        thursday = view.findViewById(R.id.thursday_Times);
        friday = view.findViewById(R.id.friday_Times);
        saturday = view.findViewById(R.id.saturday_Times);
        sunday = view.findViewById(R.id.sunday_Times);


        reff = FirebaseDatabase.getInstance().getReference().child("Employee").child(employee);
        Log.i("Employee Name", "_"+employee+"_");
        reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                  // Log.e("Error!",dataSnapshot.child("email").getValue().toString());
                  /*  employeeName.setText(dataSnapshot.child("firstName").getValue().toString() + " " + dataSnapshot.child("lastName").getValue().toString());
                    phone.setText(dataSnapshot.child("phoneNo").getValue().toString());
                    email.setText(dataSnapshot.child("email").getValue().toString());
                    position.setText(dataSnapshot.child("position").getValue().toString());
                    monday.setText(dataSnapshot.child("mondayAvailability").getValue().toString());
                    tuesday.setText(dataSnapshot.child("tuesdayAvailability").getValue().toString());
                    wednesday.setText(dataSnapshot.child("wednesdayAvailability").getValue().toString());
                    thursday.setText(dataSnapshot.child("thursdayAvailability").getValue().toString());
                    friday.setText(dataSnapshot.child("fridayAvailability").getValue().toString());
                    saturday.setText(dataSnapshot.child("saturdayAvailability").getValue().toString());
                    sunday.setText(dataSnapshot.child("sundayAvailability").getValue().toString()); */
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        return view;
    }
}
