package com.longthph30891.duan1_qlkhohang.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.longthph30891.duan1_qlkhohang.R;

public class ChangPassword_Success extends AppCompatActivity {
    Button btn_Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chang_password_success);
        btn_Back = findViewById(R.id.back_home);

        backHome();
    }

    private void backHome(){
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChangPassword_Success.this, MainActivity.class));
            }
        });
    }
}