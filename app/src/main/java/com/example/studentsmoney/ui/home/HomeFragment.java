package com.example.studentsmoney.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.studentsmoney.R;
import com.example.studentsmoney.adapters.AssetAdapter;
import com.example.studentsmoney.adapters.IncomeAdapter;
import com.example.studentsmoney.adapters.SpendAdapter;
import com.example.studentsmoney.controllers.DBController;
import com.example.studentsmoney.database.hub.Hub;
import com.example.studentsmoney.enums.Currency;
import com.example.studentsmoney.enums.Type;

public class HomeFragment extends Fragment {

    private RecyclerView incomesRV;
    private RecyclerView assetsRV;
    private RecyclerView spendsRV;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //настраиваем Tv: Баланс, Расходы и План
        TextView balanceTv = (TextView) root.findViewById(R.id.balanceTv);
        TextView expensesTv = (TextView) root.findViewById(R.id.expensesTv);
        TextView planTv = (TextView) root.findViewById(R.id.planTv);

        DBController dbController = new DBController(getContext());

        balanceTv.setText(String.valueOf(dbController.getSumOfCurrentSums()));
        expensesTv.setText(String.valueOf(dbController.getSumSpentOnMonth()));
        planTv.setText(String.valueOf(dbController.getSumOfPlannedSums()));

        //настраиваем RecyclerView (3 штуки)
        incomesRV = root.findViewById(R.id.incomesRV);
        assetsRV = root.findViewById(R.id.assetsRV);
        spendsRV = root.findViewById(R.id.spendsRV);

        LinearLayoutManager linearLayoutManagerIncome = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManagerAsset = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);

        incomesRV.setLayoutManager(linearLayoutManagerIncome);
        assetsRV.setLayoutManager(linearLayoutManagerAsset);
        spendsRV.setLayoutManager(gridLayoutManager);

        incomesRV.setHasFixedSize(true);
        assetsRV.setHasFixedSize(true);
        spendsRV.setHasFixedSize(true);

        IncomeAdapter incomeAdapter = new IncomeAdapter(getContext());
        AssetAdapter assetAdapter = new AssetAdapter(getContext());
        SpendAdapter spendAdapter = new SpendAdapter(getContext());

        incomesRV.setAdapter(incomeAdapter);
        assetsRV.setAdapter(assetAdapter);
        spendsRV.setAdapter(spendAdapter);

        //////////////
/*
        Hub hub = new Hub();
        hub.name = "основной доход";
        hub.type = Type.income;
        hub.currency = Currency.UAH;
        hub.plannedSum = 5000;
        hub.plannedSum = 10000;
        hub.area = "доход";
        hub.order = 1;

        Hub hub1 = new Hub();
        hub1.name = "приват";
        hub1.type = Type.asset;
        hub1.currency = Currency.UAH;
        //hub1.plannedSum = 5000;
        hub1.plannedSum = 15000;
        hub1.area = "";
        hub1.order = 2;

        Hub hub2 = new Hub();
        hub2.name = "еда";
        hub2.type = Type.spend;
        hub2.currency = Currency.UAH;
        hub2.plannedSum = 1200;
        hub2.plannedSum = 3000;
        hub2.area = "проживание";
        hub2.order = 3;


        dbController.insertHub(hub);
        dbController.insertHub(hub1);
        dbController.insertHub(hub2);
*/
        //////////////

        return root;
    }
}