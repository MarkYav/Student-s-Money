package com.example.studentsmoney.adapters;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentsmoney.controllers.DBController;
import com.example.studentsmoney.database.hub.Hub;
import com.example.studentsmoney.enums.Type;
import com.example.studentsmoney.ui.home.HomeFragmentDirections;
import com.example.studentsmoney.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

//This adapter for RecycleView works with incomes (look in Type enum)
public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.IncomeHolder> {

    //The list of all Hubs of a certain type, gained from DBController
    private List<Hub> incomeViews = new ArrayList<>();
    private int numberOfViews;

    public IncomeAdapter(Context context) {
        DBController controller = new DBController(context);
        this.incomeViews = controller.getAllHubsByType(Type.income);
        numberOfViews = incomeViews.size();
    }

    public IncomeAdapter(List<Hub> Views) {
        this.incomeViews = Views;
        numberOfViews = incomeViews.size();
    }

    @NonNull
    @Override
    public IncomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutID = R.layout.icon_form;

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(layoutID, parent, false);

        IncomeHolder incomeHolder = new IncomeHolder(view);

        return incomeHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeHolder holder, int position) {
        holder.bind(incomeViews.get(position));
    }

    @Override
    public int getItemCount() {
        return numberOfViews;
    }

    class IncomeHolder extends RecyclerView.ViewHolder
            implements View.OnLongClickListener, View.OnDragListener, View.OnClickListener {

        //views from the layout
        TextView nameTv;
        ImageView iconIv;
        TextView currencyTv;
        TextView sumTv;
        TextView planTv;

        public IncomeHolder(@NonNull View itemView) {
            super(itemView);

            //initialize views from .xml
            nameTv = itemView.findViewById(R.id.nameTv);
            iconIv = itemView.findViewById(R.id.iconIv);
            currencyTv = itemView.findViewById(R.id.currencyTv);
            sumTv = itemView.findViewById(R.id.sumTv);
            planTv = itemView.findViewById(R.id.planTv);

            iconIv.setOnClickListener(this);
            iconIv.setOnLongClickListener(this);
            iconIv.setOnDragListener(this);

            /*
            setting tag, it is used in dragging for determination
            relationship between views.
             */
            iconIv.setTag(Type.income);
        }

        //necessary part for a RecycleView
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
            //imgV.setColorFilter(Color.parseColor("#F53241"));
            imgV.setColorFilter(Color.RED);
            return true;
        }

        @Override
        public void onClick(View view) {
            NavController navController = Navigation.findNavController(view);

            HomeFragmentDirections.ActionNavigationHomeToListOfOperationsOfHubFragment actionNav =
                    HomeFragmentDirections.actionNavigationHomeToListOfOperationsOfHubFragment(
                            nameTv.getText().toString()
                    );
            navController.navigate(actionNav);
        }

        //TODO there is no need in this method here
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
                        //Toast.makeText(view.getContext(), "Что-то произошло!", Toast.LENGTH_SHORT).show();
                        NavController navController = Navigation.findNavController(view);
                        //navController.navigate(R.id.action_navigation_home_to_operationFragment);
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
