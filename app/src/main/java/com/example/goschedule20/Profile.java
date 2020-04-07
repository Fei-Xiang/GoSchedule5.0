package com.example.goschedule20;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Profile extends Fragment {

    TextView userName, userPhoneNumber, userEmail, userPosition, setAvailability;
    Button availability;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userId;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile, container, false);

        userName = view.findViewById(R.id.userNameHeader);
        userPhoneNumber = view.findViewById(R.id.userPhoneNumber);
        userEmail = view.findViewById(R.id.userEmail);
        userPosition = view.findViewById(R.id.userPosition);

        availability = view.findViewById(R.id.availabilityButton);
        setAvailability = view.findViewById(R.id.setAvailability);

        setAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment, new AddAvailability());
                fragmentTransaction.commit();
            }
        });
        availability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment, new AvailabilityFragment());
                fragmentTransaction.commit();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        userId = firebaseAuth.getCurrentUser().getUid();

        try {
            DocumentReference documentReference = firebaseFirestore.collection("user").document(userId);
            documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    userName.setText(documentSnapshot.getString("Name"));
                    userEmail.setText(documentSnapshot.getString("Email"));
                    userPhoneNumber.setText(documentSnapshot.getString("Phone"));
                    userPosition.setText(documentSnapshot.getString("Position"));
                    Toast.makeText(getActivity().getBaseContext(), "User defined", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch(Exception e) {
            Log.i("Error!","Error: " + e);
        }
        return view;
    }
}

