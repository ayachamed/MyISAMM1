package com.example.myisamm.ui.departments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myisamm.R;

public class DepartmentSelectionFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_department_selection, container, false);

        // Find UI elements and set listeners here if needed
        // Example: TextView welcomeText = view.findViewById(R.id.text_welcome);

        return view;
    }

    // You can add other Fragment lifecycle methods like onViewCreated, etc.
}