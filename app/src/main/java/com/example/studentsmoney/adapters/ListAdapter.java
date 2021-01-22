package com.example.studentsmoney.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentsmoney.R;
import com.example.studentsmoney.controllers.DBController;
import com.example.studentsmoney.database.hub.Hub;
import com.example.studentsmoney.database.operation.Operation;
import com.example.studentsmoney.enums.Type;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

//This adapter for RecycleView works with operation's records
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder> {

    //The list of all Operation, gained from DBController
    private List<Operation> ListViews = new ArrayList<>();
    private int numberOfViews;
    private String name;

    public ListAdapter(Context context, String name) {
        DBController controller = new DBController(context);
        this.ListViews = controller.getOperationByName(name);
        numberOfViews = ListViews.size();
        this.name = name;
    }

    @NonNull
    @NotNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutID = R.layout.operation_form;

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(layoutID, parent, false);

        ListAdapter.ListHolder listHolder = new ListAdapter.ListHolder(view);

        return listHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ListHolder holder, int position) {
        holder.bind(ListViews.get(position));
    }

    @Override
    public int getItemCount() {
        return numberOfViews;
    }

    class ListHolder extends RecyclerView.ViewHolder {

        //views from the layout
        TextView nameFromTv;
        TextView nameToTv;
        TextView descriptionTv;
        TextView currencyTv;
        TextView sumTv;

        public ListHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            //initialize views from .xml
            nameFromTv = itemView.findViewById(R.id.nameFromTv);
            nameToTv = itemView.findViewById(R.id.nameToTv);
            descriptionTv = itemView.findViewById(R.id.descriptionTv);
            currencyTv = itemView.findViewById(R.id.currencyTv);
            sumTv = itemView.findViewById(R.id.sumTv);
        }

        //a necessary part for a RecycleView
        void bind(Operation operation){
            nameFromTv.setText(operation.fromName);
            nameToTv.setText(operation.toName);
            descriptionTv.setText(operation.description);
            //currencyTv.setText(operation);
            //TODO set currency (get from DB?)
            sumTv.setText(String.valueOf(operation.sum));
        }
    }
}
