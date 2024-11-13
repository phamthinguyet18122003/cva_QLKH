package com.longthph30891.duan1_qlkhohang.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.longthph30891.duan1_qlkhohang.Activities.activitiesManagementScreen.BillListActivity;
import com.longthph30891.duan1_qlkhohang.Activities.activitiesManagementScreen.ProductListActivity;
import com.longthph30891.duan1_qlkhohang.Activities.activitiesManagementScreen.ProductTypeListActivity;
import com.longthph30891.duan1_qlkhohang.Activities.activitiesManagementScreen.UserListActivity;
import com.longthph30891.duan1_qlkhohang.DAO.userDAO;
import com.longthph30891.duan1_qlkhohang.databinding.FragmentMainFrgBinding;
public class MainFrg extends Fragment {
    private FragmentMainFrgBinding binding;
    private userDAO dao = new userDAO();
    public MainFrg() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainFrgBinding.inflate(inflater,container,false);
        initMenu();
        display();
        return binding.getRoot();
    }
    public void initMenu(){
        binding.navQlUsers.setOnClickListener(v ->{
            if(isAdmin()){
                startActivity(new Intent(getActivity(),UserListActivity.class));
            }else {
                Toast.makeText(getActivity(), "Chỉ admin", Toast.LENGTH_SHORT).show();
            }
        });
        binding.navQlProductType.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ProductTypeListActivity.class);
            startActivity(intent);
        });
        binding.navQlProducts.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), ProductListActivity.class);
            startActivity(intent);
        });
        binding.navQlBill.setOnClickListener(v ->{
            Intent intent = new Intent(getActivity(), BillListActivity.class);
            startActivity(intent);
        });
    }
    public void display(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ReLogin.txt", Context.MODE_PRIVATE);
        String usn = sharedPreferences.getString("usn","");
        binding.tvNameMain.setText("Hi "+usn);
        dao.getAvatarByUsername(usn, avatarUrl -> {
            if(getActivity() != null){
                getActivity().runOnUiThread(()->{
                    Glide.with(getActivity())
                            .load(avatarUrl)
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .into(binding.imgAvatarMain);
                });
            }
        });
    }
    public boolean isAdmin(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ReLogin.txt", Context.MODE_PRIVATE);
        int pos = sharedPreferences.getInt("pos",-1);
        if(pos==0){
            return true;
        }else {
            return false;
        }
    }
}