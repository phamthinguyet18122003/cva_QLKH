package com.longthph30891.duan1_qlkhohang.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.longthph30891.duan1_qlkhohang.Adapter.MenuAdapter;
import com.longthph30891.duan1_qlkhohang.Fragment.InventoryFrg;
import com.longthph30891.duan1_qlkhohang.Fragment.MainFrg;
import com.longthph30891.duan1_qlkhohang.Fragment.PersonalFrg;
import com.longthph30891.duan1_qlkhohang.Fragment.StockOutFrg;
import com.longthph30891.duan1_qlkhohang.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    MenuAdapter adapter;
    ViewPager2 pagerMain;
    ArrayList<Fragment>list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        pagerMain = findViewById(R.id.pagerMain);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        list.add(new MainFrg());
        list.add(new StockOutFrg());
        list.add(new InventoryFrg());
        list.add(new PersonalFrg());
        adapter = new MenuAdapter(this,list);
        pagerMain.setAdapter(adapter);
        pagerMain.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.itHome);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.itStockOut);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.itInventory);
                        break;
                    case 3:
                        bottomNavigationView.setSelectedItemId(R.id.itThongTin);
                        break;

                }
                super.onPageSelected(position);
            }
        });
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId()==R.id.itHome){
                pagerMain.setCurrentItem(0);
            }
            if (item.getItemId()==R.id.itStockOut){
                pagerMain.setCurrentItem(1);
            }
            if (item.getItemId()==R.id.itInventory){
                pagerMain.setCurrentItem(2);
            }
            if (item.getItemId()==R.id.itThongTin){
                pagerMain.setCurrentItem(3);
            }
            return true;
        });
    }
}