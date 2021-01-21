package com.example.studentsmoney.ui.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentsmoney.R;
import com.example.studentsmoney.controllers.DBController;
import com.example.studentsmoney.database.hub.Hub;
import com.example.studentsmoney.enums.Currency;

public class AddHubFragment extends Fragment {

    Hub hub;

    AddHubFragmentArgs args;

    View view;

    public AddHubFragment() {
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
        view = inflater.inflate(R.layout.fragment_add_hub, container, false);

        args = AddHubFragmentArgs.fromBundle(getArguments());

        EditText nameEt = view.findViewById(R.id.nameEt);
        TextView typeTv = view.findViewById(R.id.typeTv);
        TextView currencyTv = view.findViewById(R.id.currencyTv);
        EditText plannedSumEt = view.findViewById(R.id.plannedSumEt);
        EditText currentSumEt = view.findViewById(R.id.currentSumEt);
        EditText areaEt = view.findViewById(R.id.areaEt);
        Button addBtn = view.findViewById(R.id.addBtn);
        Button deleteBtn = view.findViewById(R.id.deleteBtn);

        typeTv.setText(args.getType().name());

        deleteBtn.setVisibility(View.INVISIBLE);

        if (args.getIntend().equals("change")) {
            DBController dbController = new DBController(getContext());
            hub = dbController.getHubByName(args.getName());

            nameEt.setText(hub.name);
            typeTv.setText(hub.type.name());
            currencyTv.setText(hub.currency.name());
            /*if (plannedSumEt.getText().toString().length() != 0){
                hub.plannedSum = Float.parseFloat(plannedSumEt.getText().toString());
            }*/
            plannedSumEt.setText(String.valueOf(hub.plannedSum));
            /*if (currentSumEt.getText().toString().length() != 0) {
                hub.currentSum = Float.parseFloat(currentSumEt.getText().toString());
            }*/
            currentSumEt.setText(String.valueOf(hub.currentSum));
            areaEt.setText(String.valueOf(hub.area));

            addBtn.setText(R.string.change);

            deleteBtn.setVisibility(View.VISIBLE);
        }

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameEt.getText().toString().length() > 0) {
                    if (args.getIntend().equals("change")) {
                        DBController dbController = new DBController(getContext());

                        hub.name = nameEt.getText().toString();
                        hub.type = args.getType();
                        hub.currency = Currency.valueOf(currencyTv.getText().toString());
                        if (plannedSumEt.getText().toString().length() != 0) {
                            hub.plannedSum = Float.parseFloat(plannedSumEt.getText().toString());
                        }
                        if (currentSumEt.getText().toString().length() != 0) {
                            hub.currentSum = Float.parseFloat(currentSumEt.getText().toString());
                        } else {
                            hub.currentSum = 0;
                        }
                        hub.area = areaEt.getText().toString();

                        dbController.update(hub);

                        getActivity().onBackPressed();
                    }

                    if (args.getIntend().equals("add")) {
                        hub = new Hub();
                        hub.name = nameEt.getText().toString();
                        hub.type = args.getType();
                        hub.currency = Currency.valueOf(currencyTv.getText().toString());
                        if (plannedSumEt.getText().toString().length() != 0) {
                            hub.plannedSum = Float.parseFloat(plannedSumEt.getText().toString());
                        }
                        if (currentSumEt.getText().toString().length() != 0) {
                            hub.currentSum = Float.parseFloat(currentSumEt.getText().toString());
                        } else {
                            hub.currentSum = 0;
                        }

                        hub.area = areaEt.getText().toString();

                        DBController dbController = new DBController(getContext());

                        hub.order = dbController.getNumberOfHubsByType(args.getType()) + 1;

                        dbController.insertHub(hub);

                        getActivity().onBackPressed();
                    }
                    //TODO проработать 3-й вариант
                } else {
                    Toast.makeText(getContext(), "Имя не введено!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBController dbController = new DBController(getContext());
                hub = dbController.getHubByName(args.getName());
                dbController.deleteHub(hub);

                getActivity().onBackPressed();
            }
        });

        return view;
    }
}