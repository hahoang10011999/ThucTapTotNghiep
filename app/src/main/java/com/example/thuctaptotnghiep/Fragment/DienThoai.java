package com.example.thuctaptotnghiep.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thuctaptotnghiep.Adapter.AdapterSanPham;
import com.example.thuctaptotnghiep.Adapter.SliderAdapterExample;
import com.example.thuctaptotnghiep.ChiTietSanPham;
import com.example.thuctaptotnghiep.Contect.SanPham;
import com.example.thuctaptotnghiep.Interface.IonClickSanPham;
import com.example.thuctaptotnghiep.R;
import com.example.thuctaptotnghiep.databinding.ActivityDienThoaiBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class DienThoai extends Fragment {
    ActivityDienThoaiBinding binding;
    AdapterSanPham adapterApple ;
    AdapterSanPham adapterSamSung ;
    AdapterSanPham adapterOppo ;
    SliderAdapterExample sliderAdapterExample;
    List<String> list = new ArrayList<>();
    String TAG = "FIREBASE";
    List<SanPham> listApple;
    List<SanPham> listSamSung;
    List<SanPham> listOppo;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static DienThoai newInstance() {
        Bundle args = new Bundle();
        DienThoai fragment = new DienThoai();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_dien_thoai, container, false);
        listApple = new ArrayList<>();
        listSamSung = new ArrayList<>();
        listOppo = new ArrayList<>();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = sharedPreferences.edit();
        sliderAdapterExample = new SliderAdapterExample( getContext());
        sliderAdapterExample.setCount(5);
        binding.imageSlider.setSliderAdapter(sliderAdapterExample);
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimations.FILL);
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        binding.imageSlider.setIndicatorSelectedColor(Color.WHITE);
        binding.imageSlider.setIndicatorUnselectedColor(Color.GRAY);
        binding.imageSlider.setScrollTimeInSec(3);
        binding.imageSlider.startAutoCycle();
        //lấy đối tượng FirebaseDatabase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
//Kết nối tới node  (node này do ta định nghĩa trong CSDL Firebase)
        DatabaseReference myRef = database.getReference("SanPham");
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
                    String ten = list.get(3);
                    String thongTin = list.get(4);
                    String soLuong = list.get(2);
                    String donGia = list.get(0);
                    String NCC = list.get(1);
                    list.clear();
                    if(key.substring(0,2).equals("DT") && NCC.equals("Apple")){
                        SanPham sp = new SanPham(key,ten,thongTin,soLuong,donGia,NCC);
                        listApple.add(sp);
                    }
                    if(key.substring(0,2).equals("DT") && NCC.equals("Samsung")){
                        SanPham sp = new SanPham(key,ten,thongTin,soLuong,donGia,NCC);
                        listSamSung.add(sp);
                    }
                    if(key.substring(0,2).equals("DT") && NCC.equals("Oppo")){
                        SanPham sp = new SanPham(key,ten,thongTin,soLuong,donGia,NCC);
                        listOppo.add(sp);
                    }

                }

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                binding.rvApple.setAdapter(adapterApple);
                binding.rvApple.setLayoutManager(layoutManager);
                RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                binding.rvSamSung.setAdapter(adapterSamSung);
                binding.rvSamSung.setLayoutManager(layoutManager2);
                RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                binding.rvOppo.setAdapter(adapterOppo);
                binding.rvOppo.setLayoutManager(layoutManager1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });
        adapterApple = new AdapterSanPham(listApple);
        adapterSamSung = new AdapterSanPham(listSamSung);
        adapterOppo = new AdapterSanPham(listOppo);
        adapterApple.setClickSanPham(new IonClickSanPham() {
            @Override
            public void onClick(SanPham sanPham) {
                editor.putString("ma",sanPham.getMaSanPham());
                editor.putString("ten",sanPham.getTenSanPham());
                editor.putString("thongTin",sanPham.getThongTinSanPham());
                editor.putString("soLuong",sanPham.getSoLuong());
                editor.putString("donGia",sanPham.getDonGia());
                editor.putString("NCC",sanPham.getNCC());
                editor.commit();
                Intent intent = new Intent(getContext(), ChiTietSanPham.class);
                startActivity(intent);
            }
        });
        adapterSamSung.setClickSanPham(new IonClickSanPham() {
            @Override
            public void onClick(SanPham sanPham) {
                editor.putString("ma",sanPham.getMaSanPham());
                editor.putString("ten",sanPham.getTenSanPham());
                editor.putString("thongTin",sanPham.getThongTinSanPham());
                editor.putString("soLuong",sanPham.getSoLuong());
                editor.putString("donGia",sanPham.getDonGia());
                editor.putString("NCC",sanPham.getNCC());
                editor.commit();
                Intent intent = new Intent(getContext(), ChiTietSanPham.class);
                startActivity(intent);
            }
        });
        adapterOppo.setClickSanPham(new IonClickSanPham() {
            @Override
            public void onClick(SanPham sanPham) {
                editor.putString("ma",sanPham.getMaSanPham());
                editor.putString("ten",sanPham.getTenSanPham());
                editor.putString("thongTin",sanPham.getThongTinSanPham());
                editor.putString("soLuong",sanPham.getSoLuong());
                editor.putString("donGia",sanPham.getDonGia());
                editor.putString("NCC",sanPham.getNCC());
                editor.commit();
                Intent intent = new Intent(getContext(), ChiTietSanPham.class);
                startActivity(intent);
            }
        });
        return binding.getRoot();
    }
}