package com.example.grocery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class exclusive_adapter extends RecyclerView.Adapter<exclusive_adapter.exclusiveview> {

    ArrayList<exclusive_model> exclusive_models;

    public exclusive_adapter(ArrayList<exclusive_model> exclusive_models) {
        this.exclusive_models = exclusive_models;
    }

    public class exclusiveview extends RecyclerView.ViewHolder
    {

        public TextView fruit_title,price_title;
        public ImageView imageView;
        public exclusiveview( View itemView) {
            super(itemView);
            fruit_title = itemView.findViewById(R.id.fruit_title);
            price_title = itemView.findViewById(R.id.price_title);
            imageView = itemView.findViewById(R.id.fruit_image);

        }
    }
    @NonNull
    @Override
    public exclusiveview onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recylerview,parent,false);
        return new exclusiveview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull exclusiveview holder, int position) {
            exclusive_model exclusive_model = exclusive_models.get(position);
            holder.fruit_title.setText(exclusive_model.getFruit_title());
            holder.price_title.setText(exclusive_model.getPrice_title());
            holder.imageView.setImageResource(exclusive_model.getImage());
    }


    @Override
    public int getItemCount() {
        return exclusive_models.size();
    }
}
