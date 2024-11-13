package com.longthph30891.duan1_qlkhohang.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.longthph30891.duan1_qlkhohang.Fragment.PersonalFrg;
import com.longthph30891.duan1_qlkhohang.R;
import com.longthph30891.duan1_qlkhohang.databinding.ActivityChangePasswordBinding;

public class Change_Password extends AppCompatActivity {
    private ActivityChangePasswordBinding binding;
    String username, idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ChangPass();
        back_ChangePass();
    }

    private void ChangPass(){
        username = getUsnFromSharedPreferences();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersCollection = db.collection("User");
        Query query = usersCollection.whereEqualTo("username", username);
        query.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for(QueryDocumentSnapshot document : task.getResult()){
                    idUser = document.getId();
                }
            }
        });

        binding.btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPass = binding.edEdOldPass.getText().toString().trim();
                String newPass = binding.edEdNewPass.getText().toString().trim();
                String rePass = binding.edEdRePass.getText().toString().trim();
                valForm();

                if(oldPass.isEmpty() || newPass.isEmpty() || rePass.isEmpty()){
                    if(oldPass.equals("")){
                        binding.inEdOldPass.setError("Vui lòng không để trống mật khẩu cũ!");
                    }else{
                        binding.inEdOldPass.setError(null);
                    }

                    if(newPass.equals("")){
                        binding.inEdNewPass.setError("Vui lòng không để trống mật khẩu mới!");
                    }else{
                        binding.inEdNewPass.setError(null);
                    }

                    if(rePass.equals("")){
                        binding.inEdRePass.setError("Vui lòng không để trống nhập lại mật khẩu!");
                    }else{
                        binding.inEdRePass.setError(null);
                    }
                }else{

                    if (newPass.length() < 10) {
                        binding.inEdNewPass.setError("Mật khẩu mới phải có ít nhất 10 ký tự!");
                        return;
                    }

                    usersCollection.document(idUser).get().addOnSuccessListener(documentSnapshot -> {
                        String currentPassword = documentSnapshot.getString("password");
                        if(!oldPass.equals(currentPassword)){
                            binding.inEdOldPass.setError("Mật khẩu cũ đã sai vui lòng nhập lại !");
                            return;
                        }

                        if(!rePass.equals(newPass)){
                            binding.inEdRePass.setError("Nhập lại mật khẩu không khớp với mật khẩu mới!");
                            return;
                        }

                        usersCollection.document(idUser).update("password",newPass)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        startActivity(new Intent(Change_Password.this, ChangPassword_Success.class));
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Change_Password.this, "Đổi mật khẩu không thành công", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    });
                }
            }
        });

    }

    private void valForm(){
        binding.edEdOldPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    binding.inEdOldPass.setError("Vui lòng không để trống mật khẩu cũ");
                }else{
                    binding.inEdOldPass.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.edEdNewPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    binding.inEdNewPass.setError("Vui lòng không để trống mật khẩu mới");
                }else{
                    binding.inEdNewPass.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.edEdRePass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    binding.inEdRePass.setError("Vui lòng nhập lại mật khẩu");
                }else{
                    binding.inEdRePass.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void back_ChangePass(){
        binding.backChangPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private String getUsnFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("ReLogin.txt", Context.MODE_PRIVATE);
        return sharedPreferences.getString("usn", "");
    }
}