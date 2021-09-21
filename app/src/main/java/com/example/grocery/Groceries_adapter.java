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

import java.util.ArrayList;

public class Groceries_adapter extends RecyclerView.Adapter<Groceries_adapter.Groceriesview> {

    ArrayList<Groceries_model> groceries_models;
    Context mContext;
    public Groceries_adapter(Context context, ArrayList<Groceries_model> groceries_models) {
        this.groceries_models = groceries_models;
        this.mContext = context;
    }

    public class Groceriesview extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView grain_name;
        LinearLayout linearLayout;

        public Groceriesview(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.grain_image);
            this.grain_name = itemView.findViewById(R.id.grain_name);
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
        holder.imageView.setImageResource(groceries_model.getImage());
        holder.linearLayout.setBackground(groceries_model.getColor());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int imageid = groceries_model.getImage();
                Intent i = new Intent(mContext,GrainDetails.class);
                i.putExtra("name",groceries_model.getGrain_title());
                i.putExtra("image",String.valueOf(imageid));
                mContext.startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return groceries_models.size();
    }
}
