package com.longthph30891.duan1_qlkhohang.Activities.activitiesManagementScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.longthph30891.duan1_qlkhohang.Adapter.BillDetailsAdapter;
import com.longthph30891.duan1_qlkhohang.Adapter.ChiTietHoaDonAdapter;
import com.longthph30891.duan1_qlkhohang.Model.Bill;
import com.longthph30891.duan1_qlkhohang.Model.BillDetail;
import com.longthph30891.duan1_qlkhohang.Model.Cart;
import com.longthph30891.duan1_qlkhohang.R;
import com.longthph30891.duan1_qlkhohang.Utilities.FormatMoney;
import com.longthph30891.duan1_qlkhohang.databinding.ActivityBillDetailsBinding;

import java.util.ArrayList;

public class BillDetailsActivity extends AppCompatActivity {
    private ActivityBillDetailsBinding binding;
    private ArrayList<BillDetail> list = new ArrayList<>();
    private ChiTietHoaDonAdapter adapter;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBillDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        Bill bill = (Bill) getIntent().getSerializableExtra("bill");
        if(bill.getStatus() == 0){
            binding.tvStatus.setText("Xuất kho");
            binding.tvStatus.setTextColor(binding.getRoot().getContext().getColor(R.color.green));
        }else if(bill.getStatus() == 1){
            binding.tvStatus.setText("Nhập kho");
            binding.tvStatus.setTextColor(binding.getRoot().getContext().getColor(R.color.red));
        }
        binding.tvNote.setText(bill.getNote());
        database = FirebaseFirestore.getInstance();
        ListenerDB(bill.getId());
        adapter = new ChiTietHoaDonAdapter(this,list,database);
        binding.rcvProductOder.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvProductOder.setAdapter(adapter);
        binding.btnBack.setOnClickListener(v -> onBackPressed());
    }
    private void ListenerDB(String billId) {
        database.collection("BillDetails")
                .whereEqualTo("billId",billId)
                .addSnapshotListener((value, error) -> {
            if (error != null) {
                return;
            }
            if (value != null) {
                int totalQuantity = 0;
                double totalPrice = 0.0;
                ArrayList<BillDetail> newList = new ArrayList<>(list);

                for (DocumentChange dc : value.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            BillDetail billDetail = dc.getDocument().toObject(BillDetail.class);
                            newList.add(billDetail);
                            break;
                        case MODIFIED:
                            BillDetail billDetail1 = dc.getDocument().toObject(BillDetail.class);
                            int oldIndex = dc.getOldIndex();

                            if (!newList.isEmpty() && oldIndex >= 0 && oldIndex < newList.size()) {
                                newList.set(oldIndex, billDetail1);
                            }
                            break;
                        case REMOVED:
                            int removedIndex = dc.getOldIndex();

                            if (!newList.isEmpty() && removedIndex >= 0 && removedIndex < newList.size()) {
                                newList.remove(removedIndex);
                            }
                            break;
                    }
                }
                list.clear();
                list.addAll(newList);
                for (BillDetail billDetail : list) {
                    totalQuantity += billDetail.getQuantity();
                    totalPrice += billDetail.getQuantity() * billDetail.getPrice();
                }
                binding.tvTotalQuantity.setText(totalQuantity + " sản phẩm");
                binding.tvTotalPrice.setText(FormatMoney.formatCurrency(totalPrice));
                if (list.isEmpty()) {
                    binding.imgNothing.setVisibility(View.VISIBLE);
                    binding.tvNothing.setVisibility(View.VISIBLE);
                } else {
                    binding.imgNothing.setVisibility(View.GONE);
                    binding.tvNothing.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}