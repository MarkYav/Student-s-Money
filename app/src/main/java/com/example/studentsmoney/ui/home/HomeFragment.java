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
        //root view for finding elements
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //setup TextViews: Balance, expenses and plan
        TextView balanceTv = (TextView) root.findViewById(R.id.balanceTv);
        TextView expensesTv = (TextView) root.findViewById(R.id.expensesTv);
        TextView planTv = (TextView) root.findViewById(R.id.planTv);

        DBController dbController = new DBController(getContext());

        balanceTv.setText(String.valueOf(dbController.getSumOfCurrentSums()));
        expensesTv.setText(String.valueOf(dbController.getSumSpentOnMonth()));
        planTv.setText(String.valueOf(dbController.getSumOfPlannedSums()));

        //setup RecyclerViews: incomesRV, assetsRV and spendsRV
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

        return root;
    }
}