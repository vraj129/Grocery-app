package com.example.grocery.Adapter;

import android.app.Activity;
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
import com.example.grocery.FavouriteActivity;
import com.example.grocery.GrainDetails;
import com.example.grocery.Model.AllStoreModel;
import com.example.grocery.R;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {

    private Context context;
    private List<AllStoreModel> modelList;
    private int act;

    public StoreAdapter(Context context, List<AllStoreModel> modelList,int act) {
        this.context = context;
        this.modelList = modelList;
        this.act = act;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoreAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.allstore_recycler,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(modelList.get(position).getImage()).into(holder.imageView);
        holder.name.setText(modelList.get(position).getName());
        holder.place.setText(modelList.get(position).getPlace());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, GrainDetails.class);
                i.putExtra("name",modelList.get(position).getName());
                i.putExtra("image",modelList.get(position).getImage());
                i.putExtra("place",modelList.get(position).getPlace());
                if(act == 1){
                    context.startActivity(i);
                    ((Activity)context).finish();
                } else if(act == 0){
                    context.startActivity(i);
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public void filterlist(List<AllStoreModel> list) {
        modelList = list;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,place;
        ImageView imageView;
        LinearLayout ll;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.fruit_title);
            imageView = itemView.findViewById(R.id.fruit_image);
            ll = itemView.findViewById(R.id.fruits_layout);
            place = itemView.findViewById(R.id.places);
        }
    }
}
