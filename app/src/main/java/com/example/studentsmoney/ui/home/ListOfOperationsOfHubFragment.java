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
import com.example.studentsmoney.adapters.SettingsAdapter;
import com.example.studentsmoney.ui.settings.ListOfHubsFragmentArgs;

public class ListOfOperationsOfHubFragment extends Fragment {

    RecyclerView listRV;

    ListOfOperationsOfHubFragmentArgs args;

    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_of_operations_of_hub, container, false);

        args = ListOfOperationsOfHubFragmentArgs.fromBundle(getArguments());

        listRV = view.findViewById(R.id.listRV);
        LinearLayoutManager linearLayoutManagerSettings = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listRV.setLayoutManager(linearLayoutManagerSettings);
        listRV.setHasFixedSize(true);
        ListAdapter listAdapter = new ListAdapter(getContext(), args.getName());
        listRV.setAdapter(listAdapter);

        return view;
    }
}