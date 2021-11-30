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
import org.w3c.dom.Text;

import java.util.ArrayList;

public class exclusive_adapter extends RecyclerView.Adapter<exclusive_adapter.exclusiveview> {

    ArrayList<exclusive_model> exclusive_models;
    Context mContext;

    public exclusive_adapter(ArrayList<exclusive_model> exclusive_models,Context context) {
        this.exclusive_models = exclusive_models;
        mContext = context;
    }

    public class exclusiveview extends RecyclerView.ViewHolder
    {

        public TextView fruit_title,price_title;
        public ImageView imageView;
        public LinearLayout linearLayout;
        public exclusiveview( View itemView) {
            super(itemView);
            fruit_title = itemView.findViewById(R.id.fruit_title);
            price_title = itemView.findViewById(R.id.price_title);
            imageView = itemView.findViewById(R.id.fruit_image);
            linearLayout = itemView.findViewById(R.id.fruits_layout);

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
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        int imageid = exclusive_model.getImage();
                    Intent i = new Intent(mContext,GrainDetails.class);
                    i.putExtra("name",exclusive_model.getFruit_title());
                    i.putExtra("image",String.valueOf(imageid));
                    mContext.startActivity(i);
                }
            });
    }


    @Override
    public int getItemCount() {
        return exclusive_models.size();
    }
}
