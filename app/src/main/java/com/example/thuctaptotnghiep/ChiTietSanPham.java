package com.example.thuctaptotnghiep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.thuctaptotnghiep.Contect.DonHang;
import com.example.thuctaptotnghiep.Contect.SanPham;
import com.example.thuctaptotnghiep.Fragment.GioHang;
import com.example.thuctaptotnghiep.R;
import com.example.thuctaptotnghiep.databinding.ActivityChiTietSanPhamBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChiTietSanPham extends AppCompatActivity {
ActivityChiTietSanPhamBinding binding;
SharedPreferences sharedPreferences;
SharedPreferences.Editor editor;
DatabaseReference mData;
String maDH;
List<String> valueSP;
List<Integer> slDon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_chi_tiet_san_pham);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setSupportActionBar(binding.toolBar);
        sharedPreferences  = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        editor = sharedPreferences.edit();
        valueSP = new ArrayList<>();
        slDon = new ArrayList<>();
        final String ma = sharedPreferences.getString("ma","");
        final String ten = sharedPreferences.getString("ten","");
        final int soLuong = Integer.parseInt(sharedPreferences.getString("soLuong",""));
        final String[] thongTin = sharedPreferences.getString("thongTin","").split(";");
        String a ="";
        final String donGia = sharedPreferences.getString("donGia","");
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String str1 = currencyVN.format(Integer.parseInt(donGia));
        binding.tvName.setText(ten);
        binding.tvGia.setText("Giá: "+str1);
        for(int i=0;i<thongTin.length;i++){
            a+=thongTin[i]+"\n";
            binding.tvThongTin.setText(a);
        }
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(ten+".jpg");
        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                binding.img.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        binding.btnThemGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sharedPreferences.getString("dangNhap","").equals("true") ){
                        mData = FirebaseDatabase.getInstance().getReference();
                        mData.child("SanPham").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot dataSnapshot :snapshot.getChildren()){
                                    for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                                        if(dataSnapshot.getKey().equals(ma)){
                                            String value = dataSnapshot1.getValue().toString();
                                            valueSP.add(value);
                                        }
                                    }
                                }
                                editor.putString("slConLai",valueSP.get(2));
                                editor.commit();
                                valueSP.clear();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        if(Integer.parseInt(sharedPreferences.getString("slConLai","")) >0){
                            mData.child("GioHang").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String key = null;
                                    for (DataSnapshot item : dataSnapshot.getChildren())
                                    {
                                        key = item.getKey();
                                        slDon.add(Integer.parseInt(key.substring(2)));
                                    }
                                    int soDH=0;
                                    for(int i=0;i<slDon.size();i++){
                                        if(slDon.get(i)>soDH){
                                            soDH = slDon.get(i);
                                        }
                                    }
                                    soDH++;
                                    int sl = Integer.parseInt(sharedPreferences.getString("slConLai",""))-1;
                                    SanPham sp = new SanPham(ten,sharedPreferences.getString("thongTin",""),String.valueOf(sl),sharedPreferences.getString("donGia",""),sharedPreferences.getString("NCC",""));
                                    mData.child("SanPham").child(ma).setValue(sp);
                                    DonHang dh = new DonHang(donGia,ma,sharedPreferences.getString("maTV",""),1,ten);
                                    String keyDH = "DH"+soDH;
                                    mData.child("GioHang").child(keyDH).setValue(dh);
                                    Toast.makeText(getBaseContext(),"Đã thêm vào giỏ hàng!!!",Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }else {
                            Toast.makeText(getBaseContext(),"Sản phẩm đã hết hàng!!!",Toast.LENGTH_LONG).show();
                        }


                }else{
                    Intent intent = new Intent(getBaseContext(),Login.class);
                    startActivity(intent);
                }
            }
        });
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}