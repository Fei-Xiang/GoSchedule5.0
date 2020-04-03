package com.example.goschedule20;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class AvailabilityFragment extends Fragment {

    ListView lv;
    ArrayList<Week> List;
    WeekAdapter weekAdapter;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userId;

    String mondayAvailability,tuesdayAvailability,wednesdayAvailability,thursdayAvailability,fridayAvailability,saturdayAvailability,sundayAvailability;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.week, container, false);

        lv = view.findViewById(R.id.lvBookList);
        List = new ArrayList<>();

        weekAdapter = new WeekAdapter(getActivity(),R.layout.item,List);
        lv.setAdapter(weekAdapter);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        userId = firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference = firebaseFirestore.collection("user").document(userId);
        documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>(){
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                mondayAvailability = documentSnapshot.getString("MondayAvailability");
                tuesdayAvailability = documentSnapshot.getString("TuesdayAvailability");
                wednesdayAvailability = documentSnapshot.getString("WednesdayAvailability");
                thursdayAvailability = documentSnapshot.getString("ThursdayAvailability");
                fridayAvailability = documentSnapshot.getString("FridayAvailability");
                saturdayAvailability = documentSnapshot.getString("SaturdayAvailability");
                sundayAvailability = documentSnapshot.getString("SundayAvailability");

                Log.i("Availability", "Monday " + mondayAvailability);
                createData(mondayAvailability,tuesdayAvailability,wednesdayAvailability,thursdayAvailability,fridayAvailability,saturdayAvailability,sundayAvailability);
            }
        });



        return  view;
    }

    /** Add data to List*/
    public void createData(String monday,String tuesday, String wednesday,String thursday, String friday, String saturday,String sunday) {

        Week week = new Week("Monday",monday);
        List.add(week);
        week = new Week("Tuesday",tuesday);
        List.add(week);
        week = new Week("Wednesday",wednesday);
        List.add(week);
        week = new Week("Thursday",thursday);
        List.add(week);
        week = new Week("Friday",friday);
        List.add(week);
        week = new Week("Saturday",saturday);
        List.add(week);
        week = new Week("Sunday",sunday);
        List.add(week);
        weekAdapter.notifyDataSetChanged();
    }
}
