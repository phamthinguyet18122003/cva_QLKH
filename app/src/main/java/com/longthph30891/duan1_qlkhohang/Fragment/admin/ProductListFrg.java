package com.longthph30891.duan1_qlkhohang.Fragment.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.longthph30891.duan1_qlkhohang.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductListFrg#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductListFrg extends Fragment {
    public ProductListFrg() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_list_frg, container, false);
    }
}