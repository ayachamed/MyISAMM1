package com.example.myisamm.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myisamm.LoginActivity;
import com.example.myisamm.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        MaterialButton logoutButton = view.findViewById(R.id.button_logout);
        logoutButton.setOnClickListener(v -> {
            logoutUser();
        });
        // Find UI elements and set listeners here if needed
        // Example: TextView welcomeText = view.findViewById(R.id.text_welcome);

        return view;
    }

    private void logoutUser() {
        // Sign out from Firebase
        FirebaseAuth.getInstance().signOut();

        // Clear any saved preferences
        SharedPreferences preferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        preferences.edit().clear().apply();

        // Start LoginActivity and clear the back stack
        Intent intent = new Intent(requireActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);


        requireActivity().finish();
    }



    // You can add other Fragment lifecycle methods like onViewCreated, etc.
}