package com.longthph30891.duan1_qlkhohang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.longthph30891.duan1_qlkhohang.Activities.activitiesManagementScreen.BillDetailsActivity;
import com.longthph30891.duan1_qlkhohang.Activities.activitiesUpdate.UpdateUserActivity;
import com.longthph30891.duan1_qlkhohang.DAO.userDAO;
import com.longthph30891.duan1_qlkhohang.Model.Bill;
import com.longthph30891.duan1_qlkhohang.R;
import com.longthph30891.duan1_qlkhohang.Utilities.FormatMoney;
import com.longthph30891.duan1_qlkhohang.databinding.ItemBillBinding;

import java.util.ArrayList;

public class billAdapter extends RecyclerView.Adapter<billAdapter.myViewHolder>{
    private final Context context;
    private final ArrayList<Bill> list;
    private userDAO dao = new userDAO();
    private FirebaseFirestore database;

    public billAdapter(Context context, ArrayList<Bill> list, FirebaseFirestore database) {
        this.context = context;
        this.list = list;
        this.database = database;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBillBinding binding = ItemBillBinding.inflate(
                LayoutInflater.from(parent.getContext()),parent,false);
        return new myViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Bill bill = list.get(position);
        holder.setData(bill);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BillDetailsActivity.class);
            intent.putExtra("bill", bill);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class myViewHolder extends RecyclerView.ViewHolder{
        private ItemBillBinding binding;
        myViewHolder(ItemBillBinding itemBillBinding) {
            super(itemBillBinding.getRoot());
            binding = itemBillBinding;
        }
        void setData(Bill bill){
            binding.tvIdBill.setText(bill.getId());
            binding.tvInvoicer.setText(bill.getCreatedByUser());
            binding.tvNote.setText(bill.getNote());
            binding.tvTotal.setText(FormatMoney.formatCurrency(bill.getTotalPrice()));
            binding.tvDay.setText(bill.getCreatedDate());
            if(bill.getStatus() == 0){
                binding.tvStatus.setText("Xuất kho");
                binding.tvStatus.setTextColor(binding.getRoot().getContext().getColor(R.color.green));
            }else if (bill.getStatus() == 1){
                binding.tvStatus.setText("Nhập kho");
                binding.tvStatus.setTextColor(binding.getRoot().getContext().getColor(R.color.red));
            }
        }
    }
}
