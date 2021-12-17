package com.example.grocery.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocery.Exclusive_model;
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
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.fruit_image);
            textView = itemView.findViewById(R.id.fruit_title);
        }
    }
}
