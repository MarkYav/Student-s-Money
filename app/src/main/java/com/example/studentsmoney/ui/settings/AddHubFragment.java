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

    //The hub which was selected
    Hub hub;

    // for getting arguments from previous fragment
    AddHubFragmentArgs args;

    //root view for navigation and finding elements
    View root;

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
        root = inflater.inflate(R.layout.fragment_add_hub, container, false);

        //receive arguments from previous fragment
        args = AddHubFragmentArgs.fromBundle(getArguments());

        //initialize views from fragment_add_hub.xml
        EditText nameEt = root.findViewById(R.id.nameEt);
        TextView typeTv = root.findViewById(R.id.typeTv);
        TextView currencyTv = root.findViewById(R.id.currencyTv);
        EditText plannedSumEt = root.findViewById(R.id.plannedSumEt);
        EditText currentSumEt = root.findViewById(R.id.currentSumEt);
        EditText areaEt = root.findViewById(R.id.areaEt);
        Button addBtn = root.findViewById(R.id.addBtn);
        Button deleteBtn = root.findViewById(R.id.deleteBtn);

        typeTv.setText(args.getType().name());

        /*
        We use this fragment either for creating or changing Hubs,
        so, firstly we set this view invisible, and if intend of this
        fragment is "change", than we set it visible.
         */
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
            //TODO check whether following EditText is filled or no
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
                        //start getting the existed Hub
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
                        //creating a new Hub
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

        //delete this Hub
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBController dbController = new DBController(getContext());
                hub = dbController.getHubByName(args.getName());
                dbController.deleteHub(hub);

                getActivity().onBackPressed();
            }
        });

        return root;
    }
}