package com.example.thuctaptotnghiep.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thuctaptotnghiep.Adapter.AdapterDonHang;
import com.example.thuctaptotnghiep.ChiTietSanPham;
import com.example.thuctaptotnghiep.Contect.DonHang;
import com.example.thuctaptotnghiep.Contect.SanPham;
import com.example.thuctaptotnghiep.Contect.ThanhVien;
import com.example.thuctaptotnghiep.Interface.IonClickDonHang;
import com.example.thuctaptotnghiep.R;
import com.example.thuctaptotnghiep.ThanhToan;
import com.example.thuctaptotnghiep.databinding.ActivityGioHangBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GioHang extends Fragment {
ActivityGioHangBinding binding;
String TAG = "FIREBASE";
AdapterDonHang adapterDonHang;
List<String> list;
List<String> ttSP = new ArrayList<>();
Map<String,DonHang> listSP;
SharedPreferences sharedPreferences;
SharedPreferences.Editor editor;
DatabaseReference mData = FirebaseDatabase.getInstance().getReference();;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_gio_hang, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = sharedPreferences.edit();
        list = new ArrayList<>();
        listSP = new HashMap<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
//Kết nối tới node  (node này do ta định nghĩa trong CSDL Firebase)
        DatabaseReference myRef = database.getReference("GioHang");
//truy suất và lắng nghe sự thay đổi dữ liệu
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //vòng lặp để lấy dữ liệu khi có sự thay đổi trên Firebase
                for (DataSnapshot data : snapshot.getChildren()) {
//lấy  dữ liệu
                    String key = data.getKey();

                    for (DataSnapshot data1 : data.getChildren()) {
                        String value = data1.getValue().toString();
                        list.add(value);
                    }
                    String soLuong = list.get(3);
                    String tenSP = list.get(4);
                    String maTV = list.get(2);
                    String maSP = list.get(1);
                    String donGia = list.get(0);
                    list.clear();

                    if(maTV.equals(sharedPreferences.getString("maTV",""))){
                        DonHang sp = new DonHang(donGia,maSP,maTV,Integer.parseInt(soLuong),tenSP);
                        listSP.put(key,sp);
                    }
                }
                if(listSP.size()==0){
                    binding.tv.setVisibility(View.VISIBLE);
                }
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),1, RecyclerView.VERTICAL, false);
                binding.rvGioHang.setAdapter(adapterDonHang);
                binding.rvGioHang.setLayoutManager(layoutManager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });
        adapterDonHang = new AdapterDonHang(listSP);
        adapterDonHang.setIonClickDonHang(new IonClickDonHang() {
            @Override
            public void onClickThem(String key, final DonHang donHang) {

                mData.child("SanPham").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                if(donHang.getMaSP().equals(dataSnapshot.getKey())){
                                    String value= dataSnapshot1.getValue().toString();
                                    ttSP.add(value);
                                }
                            }

                        }
                        editor.putString("slConLai",ttSP.get(2));
                        editor.commit();
                        ttSP.clear();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                int slConLai = Integer.parseInt(sharedPreferences.getString("slConLai",""));
                if(slConLai >0){
                    slConLai--;
                    Map<String,Object> updateSP = new HashMap<>();
                    updateSP.put("soLuong",slConLai);
                    mData.child("SanPham").child(donHang.getMaSP()).updateChildren(updateSP);
                    updateSP.clear();
                    Map<String,Object> updateDH = new HashMap<>();
                    updateDH.put("soLuong",donHang.getSoLuong()+1);
                    mData.child("GioHang").child(key).updateChildren(updateDH);
                    updateDH.clear();
                }
            }
            @Override
            public void onClickBot(String key, final DonHang donHang) {
                if(donHang.getSoLuong() >=2){
                    mData.child("SanPham").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                    if(donHang.getMaSP().equals(dataSnapshot.getKey())){
                                        String value= dataSnapshot1.getValue().toString();
                                        ttSP.add(value);
                                    }
                                }

                            }
                            editor.putString("slConLai",ttSP.get(2));
                            editor.commit();
                            ttSP.clear();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    int slConLai = Integer.parseInt(sharedPreferences.getString("slConLai",""));

                    slConLai++;
                    Map<String,Object> updateSP = new HashMap<>();
                    updateSP.put("soLuong",slConLai);
                    mData.child("SanPham").child(donHang.getMaSP()).updateChildren(updateSP);
                    updateSP.clear();
                    Map<String,Object> updateDH = new HashMap<>();
                    updateDH.put("soLuong",donHang.getSoLuong()-1);
                    mData.child("GioHang").child(key).updateChildren(updateDH);
                    updateDH.clear();
                }
            }

            @Override
            public void onClickXoa(String key, DonHang donHang,int position) {
                mData.child("GioHang").child(key).removeValue();
                listSP.remove(key);
                binding.rvGioHang.removeViewAt(position);
                adapterDonHang.notifyItemRemoved(position);
                adapterDonHang.notifyItemRangeChanged(position,listSP.size());

            }

            @Override
            public void onClickThanhToan(String key, DonHang donHang) {
                editor.putString("maDH",key);
                editor.putString("maSP",donHang.getMaSP());
                editor.putString("ten",donHang.getTenSP());
                editor.putInt("soLuong",donHang.getSoLuong());
                editor.putString("donGia",donHang.getDonGia());
                editor.putString("maTV",donHang.getMaTV());
                editor.commit();
                Intent intent = new Intent(getContext(), ThanhToan.class);
                startActivity(intent);

            }

        });
        return binding.getRoot();
    }
}