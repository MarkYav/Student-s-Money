package com.example.studentsmoney.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentsmoney.R;
import com.example.studentsmoney.controllers.DBController;
import com.example.studentsmoney.database.hub.Hub;
import com.example.studentsmoney.enums.Currency;
import com.example.studentsmoney.enums.Type;
import com.example.studentsmoney.ui.settings.ListOfHubsFragmentDirections;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.SettingsItemHolder> {

    private List<Hub> settingsViews = new ArrayList<>();
    private int numberOfViews;
    private Type type;

    public SettingsAdapter(Context context, Type type){
        DBController controller = new DBController(context);
        this.settingsViews = controller.getAllHubsByType(type);
        numberOfViews = settingsViews.size();
        this.type = type;
    }

    @NotNull
    @Override
    public SettingsItemHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutID = R.layout.hub_form;

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(layoutID, parent, false);

        SettingsItemHolder settingsItemHolder = new SettingsItemHolder(view);

        return settingsItemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SettingsItemHolder holder, int position) {
        holder.bind(settingsViews.get(position));
    }

    @Override
    public int getItemCount() {
        return numberOfViews;
    }

    class SettingsItemHolder extends RecyclerView.ViewHolder{

        TextView nameTv;
        ImageView iconIv;
        TextView currencyTv;
        TextView sumTv;
        TextView planTv;

        public SettingsItemHolder(@NonNull View itemView) {
            super(itemView);

            nameTv = itemView.findViewById(R.id.nameTv);
            iconIv = itemView.findViewById(R.id.iconIv);
            currencyTv = itemView.findViewById(R.id.currencyTv);
            sumTv = itemView.findViewById(R.id.sumTv);
            planTv = itemView.findViewById(R.id.planTv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavController navController = Navigation.findNavController(itemView);
                    ListOfHubsFragmentDirections.ActionListOfHubsFragmentToAddHubFragment actionNav =
                            ListOfHubsFragmentDirections.actionListOfHubsFragmentToAddHubFragment(
                                    "change", nameTv.getText().toString(), type
                            );
                    navController.navigate(actionNav);
                }
            });
        }

        void bind(Hub hub){
            nameTv.setText(hub.name);
            //TODO set icon
            currencyTv.setText(hub.currency.name());
            sumTv.setText(String.valueOf(hub.currentSum));
            planTv.setText(String.valueOf(hub.plannedSum));
        }
    }
}
