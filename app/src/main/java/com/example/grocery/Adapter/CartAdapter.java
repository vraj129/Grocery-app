package com.example.grocery.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.CartActivity;
import com.example.grocery.Model.CartModel;
import com.example.grocery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CartAdapter  extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context mContext;
    List<CartModel> cartModelList;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    int total_price = 0;

    public CartAdapter(Context mContext, List<CartModel> cartModelList) {
        this.mContext = mContext;
        this.cartModelList = cartModelList;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
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

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("currentUser").document(auth.getCurrentUser().getUid())
                        .collection("AddToCart")
                        .document(cartModelList.get(position).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    int actual_position  = holder.getAdapterPosition();
                                    cartModelList.remove(actual_position);
                                    notifyItemRemoved(actual_position);
                                    notifyItemRangeChanged(actual_position,cartModelList.size());
                                    Toast.makeText(mContext,"Item Deleted",Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(mContext, CartActivity.class);
                                    mContext.startActivity(i);
                                    ((Activity)mContext).finish();
                                }
                            }
                        });
            }
        });


    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,price,date,time,quantity,totalprice;
        ImageView delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.productname);
            price = itemView.findViewById(R.id.productprice);
            date = itemView.findViewById(R.id.currentdate);
            time = itemView.findViewById(R.id.currentime);
            quantity = itemView.findViewById(R.id.totalQuantity);
            totalprice = itemView.findViewById(R.id.totalprice);
            delete = itemView.findViewById(R.id.deleteitem);
        }
    }
}
