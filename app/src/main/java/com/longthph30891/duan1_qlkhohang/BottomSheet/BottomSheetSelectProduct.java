package com.longthph30891.duan1_qlkhohang.BottomSheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.longthph30891.duan1_qlkhohang.Adapter.SelectProductAdapter;
import com.longthph30891.duan1_qlkhohang.Model.Product;
import com.longthph30891.duan1_qlkhohang.Model.User;
import com.longthph30891.duan1_qlkhohang.databinding.LayoutSelectProsuctsBinding;

import java.util.ArrayList;

public class BottomSheetSelectProduct extends BottomSheetDialogFragment {
    private LayoutSelectProsuctsBinding binding;
    private FirebaseFirestore database;
    private ArrayList<Product>list = new ArrayList<>();
    private SelectProductAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LayoutSelectProsuctsBinding.inflate(inflater,container,false);
        database = FirebaseFirestore.getInstance();
        setData();
        return binding.getRoot();
    }

    private void setData() {
        ListenerDB();
        adapter = new SelectProductAdapter(getActivity(),list,database);
        binding.rcvListSelectProduct.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                        case ADDED: // thêm 1 document
                            Product product = dc.getDocument().toObject(Product.class);
                            list.add(product);
//                            adapter.notifyItemInserted(list.size() - 1);
                            break;
                        case MODIFIED: // update 1 document
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
                        case REMOVED: // xóa 1 document
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
