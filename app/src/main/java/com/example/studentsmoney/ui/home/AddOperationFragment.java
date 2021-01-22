package com.example.studentsmoney.ui.home;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentsmoney.R;
import com.example.studentsmoney.controllers.DBController;
import com.example.studentsmoney.database.hub.Hub;
import com.example.studentsmoney.database.operation.Operation;
import com.example.studentsmoney.enums.Type;

import java.util.Calendar;
import java.util.Date;

public class AddOperationFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    //calendar for datePickerDialog
    Calendar calendar;

    EditText sumEt;
    EditText editText;

    // for getting arguments from previous fragment
    AddOperationFragmentArgs args;

    //root view for navigation and finding elements
    View root;

    public AddOperationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_add_operation, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initialize views from fragment_add_operation.xml
        TextView fromTv = (TextView) getView().findViewById(R.id.fromTv);
        TextView toTv = (TextView) getView().findViewById(R.id.toTv);
        Button calendarBtn = (Button) getView().findViewById(R.id.calendarBtn);
        Button todayBtn = (Button) getView().findViewById(R.id.todayBtn);
        Button yesterdayBtn = (Button) getView().findViewById(R.id.yesterdayBtn);
        Button applyBtn = (Button) getView().findViewById(R.id.applyBtn);
        Button cancelBtn = (Button) getView().findViewById(R.id.cancelBtn);
        sumEt = (EditText) getView().findViewById(R.id.sumEt);
        editText = (EditText) getView().findViewById(R.id.editTextTextMultiLine);

        //setup OnClickListeners for buttons
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_addOperationFragment_to_navigation_home);
            }
        });

        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        todayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                if (IsDataSet()){
                    Operation(args.getFromName(), args.getToName(),
                            Float.parseFloat(sumEt.getText().toString()),
                            calendar, editText.getText().toString());
                }
            }
        });

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                if (IsDataSet()){
                    Operation(args.getFromName(), args.getToName(),
                            Float.parseFloat(sumEt.getText().toString()),
                            calendar, editText.getText().toString());
                }
            }
        });

        yesterdayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                if (IsDataSet()){
                    Operation(args.getFromName(), args.getToName(),
                            Float.parseFloat(sumEt.getText().toString()),
                            calendar, editText.getText().toString());
                }
            }
        });

        if (getArguments() != null) {
            //maybe we should put it early - in onCreateView?
            args = AddOperationFragmentArgs.fromBundle(getArguments());

            fromTv.setText(args.getFromName());
            toTv.setText(args.getToName());
        }
    }

    //pick date for operation
    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dateOfMonth) {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dateOfMonth);
        //Toast.makeText(getContext(), year + " " + month + " " + dateOfMonth, Toast.LENGTH_SHORT).show();
        if (IsDataSet()){
            Operation(args.getFromName(), args.getToName(),
                    Float.parseFloat(sumEt.getText().toString()),
                    calendar, editText.getText().toString());
        }
    }

    //it is checking is sum has set or not
    private boolean IsDataSet(){
        if (sumEt.getText().toString().length() > 0){ //TODO sumEt.getText().length() -- работает и так
            return true;
        }
        Toast.makeText(getContext(), "Введите сумму", Toast.LENGTH_SHORT).show();
        return false;
    }

    /*
    this start working at the very end

    there is adding a new record to database and navigate to navigation_home.xml
     */
    private void Operation(String from, String to, float sum, Calendar calendar, String description){
        Operation operation = new Operation();
        operation.fromName = from;
        operation.toName = to;
        operation.sum = sum;
        operation.date = calendarToDate(calendar);
        operation.description = description;

        DBController dbController = new DBController(getContext());
        dbController.insertOperation(operation);

        Hub hubFrom = dbController.getHubByName(from);
        if (hubFrom.type != Type.income) {
            hubFrom.currentSum -= sum;
        }
        else {
            hubFrom.currentSum += sum;
        }
        dbController.update(hubFrom);

        Hub hubTo = dbController.getHubByName(to);
        hubTo.currentSum += sum;
        dbController.update(hubTo);

        NavController navController = Navigation.findNavController(root);
        navController.navigate(R.id.action_addOperationFragment_to_navigation_home);
    }

    //Convert Calendar to Date
    private Date calendarToDate(Calendar calendar) {
        return calendar.getTime();
    }
}