package com.longthph30891.duan1_qlkhohang.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Intent;
import android.os.Bundle;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.longthph30891.duan1_qlkhohang.Activities.activitiesCreate.CreateBillActivity;
import com.longthph30891.duan1_qlkhohang.Adapter.SelectProductAdapter;
import com.longthph30891.duan1_qlkhohang.Model.Product;
import com.longthph30891.duan1_qlkhohang.databinding.ActivitySelectProductBinding;
import java.util.ArrayList;

public class SelectProductActivity extends AppCompatActivity {
    private ActivitySelectProductBinding binding;
    private FirebaseFirestore database;
    private ArrayList<Product> list = new ArrayList<>();
    private SelectProductAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseFirestore.getInstance();
        setData();
    }
    private void setData() {
        binding.btnBack.setOnClickListener(v -> {
            startActivity(new Intent(this, CreateBillActivity.class));

        });
        ListenerDB();
        adapter = new SelectProductAdapter(this,list,database);
        binding.rcvListSelectProduct.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvListSelectProduct.setAdapter(adapter);
        binding.rcvListSelectProduct.setAdapter(adapter);
    }
    private void ListenerDB(){
        database.collection("Product").addSnapshotListener((value, error) -> {
            if(error != null){
                return;
            }
            if(value != null){
                for(DocumentChange dc : value.getDocumentChanges()){
                    switch (dc.getType()){
                        case ADDED:
                            Product product = dc.getDocument().toObject(Product.class);
                            list.add(product);
                            break;
                        case MODIFIED:
                            Product pUpdate = dc.getDocument().toObject(Product.class);
                            if (dc.getOldIndex() == dc.getNewIndex()) {
                                list.set(dc.getOldIndex(), pUpdate);
                                adapter.notifyItemChanged(dc.getOldIndex());
                            } else {
                                list.remove(dc.getOldIndex());
                                list.add(pUpdate);
                                adapter.notifyItemMoved(dc.getOldIndex(),dc.getNewIndex());
                            }
                            break;
                        case REMOVED:
                            dc.getDocument().toObject(Product.class);
                            list.remove(dc.getOldIndex());
                            adapter.notifyItemRemoved(dc.getOldIndex());
                            break;
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}