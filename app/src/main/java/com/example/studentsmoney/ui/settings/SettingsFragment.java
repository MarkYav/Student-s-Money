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

    //The type of Hubs, which are wanted to manage
    //(it means that all Hubs here have the same type)
    Type type;

    //root view for navigation and finding elements
    View root;

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
        root = inflater.inflate(R.layout.fragment_settings, container, false);

        //initialize views from fragment_settings.xml
        Button incomeManagerBtn = root.findViewById(R.id.incomeManagerBtn);
        Button assetManagerBtn = root.findViewById(R.id.assetManagerBtn);
        Button spendManagerBtn = root.findViewById(R.id.spendManagerBtn);

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

        return root;
    }

    //function that navigate to the list of Hubs of a certain type
    private void navigate(){
        NavController navController = Navigation.findNavController(root);
        SettingsFragmentDirections.ActionNavigationSettingsToListOfHubsFragment actionNav =
                SettingsFragmentDirections.actionNavigationSettingsToListOfHubsFragment(type);
        navController.navigate(actionNav);
    }
}