package com.longthph30891.duan1_qlkhohang.Activities.activitiesManagementScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.longthph30891.duan1_qlkhohang.Activities.MainActivity;
import com.longthph30891.duan1_qlkhohang.Activities.activitiesCreate.CreateProductTypeActivity;
import com.longthph30891.duan1_qlkhohang.Adapter.ProTypeAdapter;
import com.longthph30891.duan1_qlkhohang.Model.ProductType;
import com.longthph30891.duan1_qlkhohang.Model.User;
import com.longthph30891.duan1_qlkhohang.R;
import com.longthph30891.duan1_qlkhohang.databinding.ActivityProductTypeListBinding;

import java.util.ArrayList;

public class ProductTypeListActivity extends AppCompatActivity {
    private ActivityProductTypeListBinding binding;
    private FirebaseFirestore database;
    private ArrayList<ProductType>list = new ArrayList<>();

    private ProTypeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductTypeListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseFirestore.getInstance();
        setListener();
    }
    private void setListener(){
        setSupportActionBar(binding.toolbarProType);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //
        ListenerDB();
        adapter = new ProTypeAdapter(this,list,database);
        binding.rcvProductType.setLayoutManager(new GridLayoutManager(this,2));
        binding.rcvProductType.setAdapter(adapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.sub_menu,menu);
        MenuItem itemAdd = menu.findItem(R.id.item_add);
        itemAdd.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
               if(item.getItemId() == R.id.item_add){
                   startActivity(new Intent(ProductTypeListActivity.this, CreateProductTypeActivity.class));
               }

               return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        return true;
    }
    private void ListenerDB(){
        database.collection("ProductType").addSnapshotListener((value, error) -> {
            if(error != null){
                return;
            }
            if(value != null){
                for(DocumentChange dc : value.getDocumentChanges()){
                    switch (dc.getType()){
                        case ADDED: // thêm 1 document
                            ProductType productType = dc.getDocument().toObject(ProductType.class);
                            list.add(productType);
                            adapter.notifyItemInserted(list.size() - 1);
                            break;
                        case MODIFIED: // update 1 document
                            ProductType productTypeUpdate = dc.getDocument().toObject(ProductType.class);
                            if (dc.getOldIndex() == dc.getNewIndex()) {
                                list.set(dc.getOldIndex(), productTypeUpdate);
                                adapter.notifyItemChanged(dc.getOldIndex());
                            } else {
                                list.remove(dc.getOldIndex());
                                list.add(productTypeUpdate);
                                adapter.notifyItemMoved(dc.getOldIndex(),dc.getNewIndex());
                            }
                            break;
                        case REMOVED: // xóa 1 document
                            dc.getDocument().toObject(ProductType.class);
                            list.remove(dc.getOldIndex());
                            adapter.notifyItemRemoved(dc.getOldIndex());
                            break;
                    }
                }
            }
        });
    }
}