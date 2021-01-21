package com.example.studentsmoney.ui.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.studentsmoney.R;
import com.example.studentsmoney.enums.Type;

public class SettingsFragment extends Fragment {

    Type type;

    View view;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        //return view;

        Button incomeManagerBtn = view.findViewById(R.id.incomeManagerBtn);
        Button assetManagerBtn = view.findViewById(R.id.assetManagerBtn);
        Button spendManagerBtn = view.findViewById(R.id.spendManagerBtn);

        incomeManagerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = Type.income;
                navigate();
            }
        });

        assetManagerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = Type.asset;
                navigate();
            }
        });

        spendManagerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = Type.spend;
                navigate();
            }
        });

        return view;
    }

    private void navigate(){
        NavController navController = Navigation.findNavController(view);
        SettingsFragmentDirections.ActionNavigationSettingsToListOfHubsFragment actionNav =
                SettingsFragmentDirections.actionNavigationSettingsToListOfHubsFragment(type);
        navController.navigate(actionNav);
    }
}