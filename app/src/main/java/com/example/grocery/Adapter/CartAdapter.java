package com.example.grocery.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.Model.CartModel;
import com.example.grocery.R;

import java.util.List;

public class CartAdapter  extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context mContext;
    List<CartModel> cartModelList;

    public CartAdapter(Context mContext, List<CartModel> cartModelList) {
        this.mContext = mContext;
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(cartModelList.get(position).getProductName());
        holder.price.setText(cartModelList.get(position).getproductPrice());
        holder.date.setText(cartModelList.get(position).getCurrentDate());
        holder.time.setText(cartModelList.get(position).getCurrentTime());
        holder.quantity.setText(cartModelList.get(position).getTotalQuantity());
        holder.totalprice.setText(cartModelList.get(position).getTotalPrice());

    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,price,date,time,quantity,totalprice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.productname);
            price = itemView.findViewById(R.id.productprice);
            date = itemView.findViewById(R.id.currentdate);
            time = itemView.findViewById(R.id.currentime);
            quantity = itemView.findViewById(R.id.totalQuantity);
            totalprice = itemView.findViewById(R.id.totalprice);
        }
    }
}
