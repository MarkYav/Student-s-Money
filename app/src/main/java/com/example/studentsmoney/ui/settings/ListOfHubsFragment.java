package com.example.studentsmoney.ui.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.studentsmoney.R;
import com.example.studentsmoney.adapters.SettingsAdapter;

public class ListOfHubsFragment extends Fragment {

    //the main RecyclerView
    RecyclerView settingsRV;

    // for getting arguments from previous fragment
    ListOfHubsFragmentArgs args;

    //root view for navigation and finding elements
    View root;

    public ListOfHubsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_list_of_hubs, container, false);

        //receive arguments from previous fragment
        args = ListOfHubsFragmentArgs.fromBundle(getArguments());

        //initialize views from fragment_list_of_operations_of_hub.xml
        settingsRV = root.findViewById(R.id.settingsRV);
        LinearLayoutManager linearLayoutManagerSettings = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        settingsRV.setLayoutManager(linearLayoutManagerSettings);
        settingsRV.setHasFixedSize(true);
        SettingsAdapter settingsAdapter = new SettingsAdapter(getContext(), args.getType());
        settingsRV.setAdapter(settingsAdapter);

        Button addHubBtn = root.findViewById(R.id.addHubBtn);
        addHubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                ListOfHubsFragmentDirections.ActionListOfHubsFragmentToAddHubFragment actionNav =
                        ListOfHubsFragmentDirections.actionListOfHubsFragmentToAddHubFragment(
                                "add", null, args.getType()
                        );
                navController.navigate(actionNav);
            }
        });

        return root;
    }
}