package com.example.studentsmoney.adapters;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentsmoney.controllers.DBController;
import com.example.studentsmoney.database.hub.Hub;
import com.example.studentsmoney.enums.Type;
import com.example.studentsmoney.ui.home.HomeFragmentDirections;
import com.example.studentsmoney.R;

import java.util.ArrayList;
import java.util.List;

//This adapter for RecycleView works with assets (look in Type enum)
public class AssetAdapter extends RecyclerView.Adapter<AssetAdapter.AssetHolder> {

    //The list of all Hubs of a certain type, gained from DBController
    private List<Hub> assetViews = new ArrayList<>();
    private int numberOfViews;

    public AssetAdapter(Context context) {
        DBController controller = new DBController(context);
        this.assetViews = controller.getAllHubsByType(Type.asset);
        numberOfViews = assetViews.size();
    }

    public AssetAdapter(List<Hub> Views) {
        this.assetViews = Views;
        numberOfViews = assetViews.size();
    }

    @NonNull
    @Override
    public AssetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutID = R.layout.icon_form;

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(layoutID, parent, false);

        AssetHolder assetHolder = new AssetHolder(view);

        return assetHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AssetHolder holder, int position) {
        holder.bind(assetViews.get(position));
    }


    @Override
    public int getItemCount() {
        return numberOfViews;
    }

    class AssetHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnDragListener {

        //views from the layout
        TextView nameTv;
        ImageView iconIv;
        TextView currencyTv;
        TextView sumTv;
        TextView planTv;

        public AssetHolder(@NonNull View itemView) {
            super(itemView);

            //initialize views from .xml
            nameTv = itemView.findViewById(R.id.nameTv);
            iconIv = itemView.findViewById(R.id.iconIv);
            currencyTv = itemView.findViewById(R.id.currencyTv);
            sumTv = itemView.findViewById(R.id.sumTv);
            planTv = itemView.findViewById(R.id.planTv);

            iconIv.setOnLongClickListener(this);
            iconIv.setOnDragListener(this);

            /*
            setting tag, it is used in dragging for determination
            relationship between views.
             */
            iconIv.setTag(Type.asset);

            iconIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavController navController = Navigation.findNavController(view);

                    HomeFragmentDirections.ActionNavigationHomeToListOfOperationsOfHubFragment actionNav =
                            HomeFragmentDirections.actionNavigationHomeToListOfOperationsOfHubFragment(
                                    nameTv.getText().toString()
                            );
                    navController.navigate(actionNav);
                }
            });
        }

        //a necessary part for a RecycleView
        void bind(Hub hub) {
            nameTv.setText(hub.name);
            //TODO set icon
            currencyTv.setText(hub.currency.name());
            sumTv.setText(String.valueOf(hub.currentSum));
            planTv.setText(String.valueOf(hub.plannedSum));

            iconIv.setTag(R.id.nameTv, hub.name);
        }

        /*
        Now, it is a trigger for drugging, but we should
        change it in future versions (to ~onTouch??)
         */
        @Override
        public boolean onLongClick(View view) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data//data to be dragged
                    , shadowBuilder //drag shadow
                    , view//local data about the drag and drop operation
                    , 0//no needed flags
            );

            ImageView imgV = (ImageView) view;
            imgV.setColorFilter(Color.RED);
            return true;
        }

        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            int action = dragEvent.getAction();
            ImageView imgV = (ImageView) view;
            ImageView dragView = (ImageView) dragEvent.getLocalState();
            // Handles each of the expected events
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    if (check(imgV, dragView)) {
                        imgV.setColorFilter(Color.BLACK);
                        //dragView.setColorFilter(Color.RED);
                        imgV.invalidate();
                    }
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    if (check(imgV, dragView)) {
                        imgV.clearColorFilter();
                        //dragView.setColorFilter(Color.RED);
                        imgV.invalidate();
                    }
                    return true;
                case DragEvent.ACTION_DROP:
                    if (check(imgV, dragView)) {
                        NavController navController = Navigation.findNavController(view);
                        HomeFragmentDirections.ActionNavigationHomeToAddOperationFragment actionNav =
                                HomeFragmentDirections.actionNavigationHomeToAddOperationFragment(
                                        dragView.getTag(R.id.nameTv).toString(), imgV.getTag(R.id.nameTv).toString());
                        navController.navigate(actionNav);
                    }
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    imgV.clearColorFilter();
                    imgV.invalidate();
                    dragView.clearColorFilter();
                    dragView.invalidate();
                    return true;

                // An unknown action type was received.
                default:
                    Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                    break;
            }
            return false;
        }

        /*
        checking method - is created to prevent unnecessary interaction
         */
        private boolean check(ImageView imgV, ImageView dragView) {
            if (imgV.getTag(R.id.nameTv).toString().equals(dragView.getTag(R.id.nameTv).toString()))
                return false;
            if (dragView.getTag() == Type.income && imgV.getTag() == Type.asset)
                return true;
            if (dragView.getTag() == Type.asset && imgV.getTag() == Type.spend)
                return true;
            if (dragView.getTag() == Type.asset && imgV.getTag() == Type.asset)
                return true;
            return false;
        }
    }
}
