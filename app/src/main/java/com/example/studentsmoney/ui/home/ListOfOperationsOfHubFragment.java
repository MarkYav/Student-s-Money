package com.example.studentsmoney.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studentsmoney.R;
import com.example.studentsmoney.adapters.ListAdapter;

public class ListOfOperationsOfHubFragment extends Fragment {

    //the main RecyclerView
    RecyclerView listRV;

    // for getting arguments from previous fragment
    ListOfOperationsOfHubFragmentArgs args;

    //root view for navigation and finding elements
    View root;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_list_of_operations_of_hub, container, false);

        //receive arguments from previous fragment
        args = ListOfOperationsOfHubFragmentArgs.fromBundle(getArguments());

        //initialize views from fragment_list_of_operations_of_hub.xml
        listRV = root.findViewById(R.id.listRV);
        LinearLayoutManager linearLayoutManagerSettings = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listRV.setLayoutManager(linearLayoutManagerSettings);
        listRV.setHasFixedSize(true);
        ListAdapter listAdapter = new ListAdapter(getContext(), args.getName());
        listRV.setAdapter(listAdapter);

        return root;
    }
}