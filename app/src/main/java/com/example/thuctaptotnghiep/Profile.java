package com.example.thuctaptotnghiep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.thuctaptotnghiep.Contect.ThanhVien;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Profile extends AppCompatActivity {
    EditText ten,diaChi,sdt,mkCu,mkMoi,nhapLai;
    Toolbar toolbar;
    Button btnSua,btnLuu,btnDangXuat,btnDoiMK,btnLuuMK;
    ImageView imgBack;
    LinearLayout ln;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DatabaseReference mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setSupportActionBar(toolbar);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        editor = sharedPreferences.edit();
        ln = findViewById(R.id.LnMK);
        mkCu = findViewById(R.id.edMKCu);
        mkMoi = findViewById(R.id.edMKMoi);
        nhapLai = findViewById(R.id.edXacNhanMK);
        imgBack = findViewById(R.id.back);
        btnDoiMK = findViewById(R.id.btnDoiMK);
        btnLuuMK = findViewById(R.id.btnLuuMK);
        ten = findViewById(R.id.hoTen);
        diaChi = findViewById(R.id.diaChi);
        sdt = findViewById(R.id.sdt);
        toolbar = findViewById(R.id.toolBar);
        ten.setText(sharedPreferences.getString("tenTV",""));
        diaChi.setText(sharedPreferences.getString("diaChi",""));
        sdt.setText(sharedPreferences.getString("sdt",""));
        btnLuu = findViewById(R.id.btnLuu);
        btnSua = findViewById(R.id.btnSua);
        btnDangXuat = findViewById(R.id.btnDangXuat);
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ten.setEnabled(true);
                diaChi.setEnabled(true);
                sdt.setEnabled(true);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                editor.putString("quyen","");
                editor.putString("maTV","");
                editor.putString("dangNhap","false");
                editor.putString("tenTV", "");
                editor.putString("diaChi","");
                editor.putString("sdt","");
                editor.commit();
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mData = FirebaseDatabase.getInstance().getReference("ThanhVien");
                ThanhVien tv = new ThanhVien(ten.getText().toString(),diaChi.getText().toString(),sharedPreferences.getString("tk",""),sharedPreferences.getString("mk",""),"TV",sdt.getText().toString());
                mData.child(sharedPreferences.getString("maTV","")).setValue(tv);
                Toast.makeText(getBaseContext(),"Cập nhật thông tin thành công !!!",Toast.LENGTH_LONG).show();
                ten.setEnabled(false);
                diaChi.setEnabled(false);
                sdt.setEnabled(false);
            }
        });
        btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ln.setVisibility(View.VISIBLE);
            }
        });
        btnLuuMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mkMoi.getText().toString().equals(nhapLai.getText().toString())){
                mData = FirebaseDatabase.getInstance().getReference("ThanhVien");
                ThanhVien tv = new ThanhVien(ten.getText().toString(),diaChi.getText().toString(),sharedPreferences.getString("tk",""),mkMoi.getText().toString(),"TV",sdt.getText().toString());
                mData.child(sharedPreferences.getString("maTV","")).setValue(tv);
                Toast.makeText(getBaseContext(),"Cập nhật thông tin thành công !!!",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getBaseContext(),"Nhập lại chưa chính xác !!!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}