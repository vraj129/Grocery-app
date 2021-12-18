package com.example.grocery;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Groceries_adapter extends RecyclerView.Adapter<Groceries_adapter.Groceriesview> {

    List<Groceries_model> groceries_models;
    Context mContext;
    public Groceries_adapter(Context context, List<Groceries_model> groceries_models) {
        this.groceries_models = groceries_models;
        this.mContext = context;
    }

    public class Groceriesview extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView grain_name,place;
        LinearLayout linearLayout;

        public Groceriesview(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.grain_image);
            this.grain_name = itemView.findViewById(R.id.grain_name);
            this.place = itemView.findViewById(R.id.places);
            this.linearLayout = itemView.findViewById(R.id.background_color);
        }
    }


    @NonNull
    @Override
    public Groceriesview onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.groceries_recycler_view,parent,false);

        return new Groceriesview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  Groceries_adapter.Groceriesview holder, int position) {
        Groceries_model groceries_model = groceries_models.get(position);
        holder.grain_name.setText(groceries_model.getGrain_title());
        holder.place.setText(groceries_model.getPlace());
        Glide.with(mContext).load(groceries_models.get(position).getImage()).into(holder.imageView);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext,GrainDetails.class);
                i.putExtra("name",groceries_model.getGrain_title());
                i.putExtra("image",groceries_model.getImage());
                i.putExtra("place",groceries_model.getPlace());
                mContext.startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return groceries_models.size();
    }
}
