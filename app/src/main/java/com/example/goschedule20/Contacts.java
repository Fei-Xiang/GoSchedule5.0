package com.example.goschedule20;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Contacts extends Fragment {

    DatabaseReference reff;
    DatabaseReference employees;
    ListView contacts;

    ArrayList<String> arrayContacts = new ArrayList<>();
    ArrayAdapter<String> adapterContacts;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contacts, container, false);

        adapterContacts = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, arrayContacts);
        contacts = view.findViewById(R.id.instoreEmployees);
        contacts.setAdapter(adapterContacts);



        reff = FirebaseDatabase.getInstance().getReference();
        employees = reff.child("Employee");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        String fullName = ds.child("fullName").getValue(String.class);
                        arrayContacts.add(fullName);
                        adapterContacts.notifyDataSetChanged();
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        employees.addListenerForSingleValueEvent(eventListener);

        contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = (String) contacts.getItemAtPosition(i);
                SeeContact fragment = new SeeContact();
                Bundle bundle = new Bundle();
                bundle.putString("EmployeeName",name);
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment, fragment);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}
