package com.example.thuctaptotnghiep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thuctaptotnghiep.Contect.DonHang;
import com.example.thuctaptotnghiep.Contect.ThanhVien;
import com.example.thuctaptotnghiep.databinding.ActivityDangKyBinding;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DangKy extends AppCompatActivity {
ActivityDangKyBinding binding;
DatabaseReference mData;
SharedPreferences sharedPreferences;
SharedPreferences.Editor editor;
Handler handler;
boolean check = true;
List<String> listTK ,list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_dang_ky);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setSupportActionBar(binding.toolBar);
        handler = new Handler();
        listTK = new ArrayList<>();
        list = new ArrayList<>();
        sharedPreferences  = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        editor = sharedPreferences.edit();
        binding.btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkNull(binding.hoTen.getText().toString(),binding.layoutTen);
                checkNull(binding.diaChi.getText().toString(),binding.layoutDiaChi);
                checkNull(binding.sdt.getText().toString(),binding.layoutSDT);
                checkNull(binding.tk.getText().toString(),binding.layoutTK);
                checkNull(binding.mk.getText().toString(),binding.layoutMK);
                checkNull(binding.nhapLaimk.getText().toString(),binding.layoutXacNhan);
                if(!binding.hoTen.getText().toString().isEmpty() && !binding.diaChi.getText().toString().isEmpty() && !binding.sdt.getText().toString().isEmpty() && !binding.tk.getText().toString().isEmpty()
                        && !binding.mk.getText().toString().isEmpty() && !binding.nhapLaimk.getText().toString().isEmpty()){
                    mData = FirebaseDatabase.getInstance().getReference("ThanhVien");
                    mData.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String key = null;
                            for (DataSnapshot item : dataSnapshot.getChildren())
                            {
                                key = item.getKey();
                                for(DataSnapshot dataSnapshot1:item.getChildren()){
                                    String value = dataSnapshot1.getValue().toString();
                                    list.add(value);
                                }
                                listTK.add(list.get(5));
                                list.clear();
                            }
                            for(int i=0;i<listTK.size();i++){
                                if(listTK.get(i).equals(binding.tk.getText().toString())){
                                    check = false;
                                }
                            }
                            if(check==false){
                                Toast.makeText(getBaseContext(),"Tên tài khoản đã có người sử dụng, vui lòng chọn tên khác!!",Toast.LENGTH_LONG).show();
                            }
                            if(!binding.mk.getText().toString().equals(binding.nhapLaimk.getText().toString())){
                                binding.layoutXacNhan.setError("Nhập lại chưa chính xác!!");
                                ShowError3s showError3s = new ShowError3s();
                                handler.postDelayed(showError3s,3000);
                            }
                            if(check && binding.mk.getText().toString().equals(binding.nhapLaimk.getText().toString())){
                                int soTV = Integer.parseInt(key.substring(2))+1;
                                ThanhVien tv = new ThanhVien(binding.hoTen.getText().toString(),binding.diaChi.getText().toString(),binding.tk.getText().toString(),binding.mk.getText().toString(),"TV",binding.sdt.getText().toString());
                                String keyTV = "TV"+soTV;
                                mData.child(keyTV).setValue(tv);
                                finish();
                                Intent intent = new Intent(getBaseContext(),Login.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }


            }
        });
    }
    public void checkNull(String text, TextInputLayout layout){
        if(text.isEmpty()){
            layout.setError("Vui lòng điền đầy đủ thông tin");
            ShowError3s showError3s = new ShowError3s();
            handler.postDelayed(showError3s,3000);
        }
    }
    class ShowError3s implements Runnable {

        @Override
        public void run() {
            handler.postDelayed(this, 3000);
            binding.layoutMK.setError(null);
            binding.layoutTK.setError(null);
            binding.layoutDiaChi.setError(null);
            binding.layoutSDT.setError(null);
            binding.layoutTen.setError(null);
            binding.layoutXacNhan.setError(null);
        }
    }
}