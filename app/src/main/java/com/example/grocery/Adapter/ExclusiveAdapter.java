package com.example.grocery.Adapter;

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
import com.example.grocery.Exclusive_model;
import com.example.grocery.GrainDetails;
import com.example.grocery.R;

import java.util.List;

public class ExclusiveAdapter extends RecyclerView.Adapter<ExclusiveAdapter.ViewHolder> {

    private Context context;
    private List<Exclusive_model> modelList;

    public ExclusiveAdapter(Context context, List<Exclusive_model> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ExclusiveAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recylerview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExclusiveAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(modelList.get(position).getImage()).into(holder.imageView);
        holder.textView.setText(modelList.get(position).getFruit_title());
        holder.places.setText(modelList.get(position).getPlace());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String image_url = modelList.get(position).getImage();
                String name = modelList.get(position).getFruit_title();
                String place = modelList.get(position).getPlace();
                Intent i = new Intent(context, GrainDetails.class);
                i.putExtra("name",name);
                i.putExtra("image",image_url);
                i.putExtra("place",place);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView,places;
        LinearLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.fruit_image);
            textView = itemView.findViewById(R.id.fruit_title);
            layout = itemView.findViewById(R.id.layout);
            places = itemView.findViewById(R.id.places);
        }
    }
}
