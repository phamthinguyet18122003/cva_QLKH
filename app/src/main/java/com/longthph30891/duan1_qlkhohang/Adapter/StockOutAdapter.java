package com.longthph30891.duan1_qlkhohang.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.longthph30891.duan1_qlkhohang.Model.Bill;
import com.longthph30891.duan1_qlkhohang.Model.StockOut;
import com.longthph30891.duan1_qlkhohang.R;
import com.longthph30891.duan1_qlkhohang.databinding.ItemStockOutBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class StockOutAdapter extends RecyclerView.Adapter<StockOutAdapter.viewHolep> {
    Context context;
    ArrayList<StockOut> list;
    FirebaseFirestore database;

    public StockOutAdapter(Context context, ArrayList<StockOut> list, FirebaseFirestore database) {
        this.context = context;
        this.list = list;
        this.database = database;
    }

    @NonNull
    @Override
    public viewHolep onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_stock_out, null);
        return new viewHolep(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolep holder, int position) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.### đ");
        StockOut stockOut = list.get(position);
        Glide.with(context).load(stockOut.getImageProduct()).into(holder.imgAnh);
        holder.tvTenSP.setText(stockOut.getNameProduct());
        holder.tvTienSP.setText(decimalFormat.format(stockOut.getPrice()));
        holder.tvSoLuong.setText(String.valueOf(stockOut.getQuantity()));
//        Log.d("tiensp", String.valueOf(list.get(position).getPriceProduct()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolep extends RecyclerView.ViewHolder {
        ImageView imgAnh;
        TextView tvTenSP, tvTienSP, tvSoLuong;

        public viewHolep(@NonNull View itemView) {
            super(itemView);
            imgAnh = itemView.findViewById(R.id.imgProductSTO);
            tvTenSP = itemView.findViewById(R.id.tvProductNameSTO);
            tvTienSP = itemView.findViewById(R.id.tvProductPriceSTO);
            tvSoLuong = itemView.findViewById(R.id.tvProductQuantitySTO);
        }
    }
}
