package com.example.blabla.ui.main;

import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blabla.R;
import com.example.blabla.model.Event;
import com.example.blabla.model.LongLat;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    private static final String TAG = "EventsAdapter";
    public List<Event> dataSet;
    public LatLng location;

    public EventsAdapter(List<Event> dataSet) {
        this.dataSet = dataSet;
    }

    public EventsAdapter(List<Event> dataSet,LatLng location) {
        this.dataSet = dataSet;
        this.location=location;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private String id;
        private final TextView title;
        private final TextView description;
        private final CardView card;
        private final TextView distance;
        private final ChipGroup tags;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.eventTitle);
            description = itemView.findViewById(R.id.eventDescription);
            card = itemView.findViewById(R.id.eventCard);
            distance = itemView.findViewById(R.id.distance);
            tags = itemView.findViewById(R.id.tags);
            card.setOnClickListener(v -> {
                Log.i(TAG, "ViewHolder: onclick "+getAdapterPosition()+ " "+id);
                Navigation.findNavController(itemView).navigate(EventsListFragmentDirections.actionEventsListFragmentToEventFragment(id));
            });

        }
         public TextView getTitle() {
            return title;
        }

        public TextView getDescription() {
            return description;
        }

        public CardView getCard() {
            return card;
        }

        public TextView getDistance() {
            return distance;
        }

        public ChipGroup getTags() {
            return tags;
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = dataSet.get(position);
        holder.getCard().setAnimation(AnimationUtils.loadAnimation(holder.getCard().getContext(),R.anim.fade_transition));
        holder.getTitle().setText(event.getTitle());
        holder.getDescription().setText(event.getDescription());
        if(event.getTags()!=null){
            Arrays.stream(event.getTags()).forEach(tag -> {
                Chip chip = new Chip(holder.getTags().getContext());
                chip.setText(tag.getValue());
                holder.getTags().addView(chip);
            });
        }
        holder.id = event.getId();
        double distance = event.getDistance();
        if(distance!=0) {
            DecimalFormat df = new DecimalFormat("#.##");
            holder.getDistance().setText(df.format((distance / 1000)) + " km");
        }
//        if(this.location!=null){
//            Log.i(TAG, "onBindViewHolder: locationnotnull");
////            Location start = new Location("start");
////            start.setLongitude(longlat.getLongitude());
////            start.setLatitude(longlat.getLatitude());
////            Location end = new Location("end");
////            end.setLongitude(location.longitude);
////            end.setLatitude(location.latitude);
////            float result = start.distanceTo(end);
////            Log.i(TAG, "onBindViewHolder: "+event.getAddress());
//////            36.880400, 10.221458
////            Log.i(TAG, "onBindViewHolder: "+coordinates.get(0));
////            Log.i(TAG, "onBindViewHolder: "+coordinates.get(1));
////            Log.i(TAG, "onBindViewHolder: "+location.longitude);
////            Log.i(TAG, "onBindViewHolder: "+location.latitude);
////            Log.i(TAG, "onBindViewHolder: result : "+result);
////            Log.i(TAG, "*************************");
//
////            float[] results = new float[3];
////            Location.distanceBetween(location.latitude,location.longitude,coordinates.get(0),coordinates.get(1),results);
////            Log.i(TAG, "onBindViewHolder: "+results[0]);
////            Log.i(TAG, "onBindViewHolder: "+results[1]);
////            Log.i(TAG, "onBindViewHolder: "+results[2]);
////            Log.i(TAG, "***********************************");
////            holder.getDistance().setText(String.valueOf(results[0]/1000));
////            DecimalFormat df = new DecimalFormat("#.##");
//            holder.getDistance().setText((distance/1000)+" km");
//        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}
