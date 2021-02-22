package com.example.thuctaptotnghiep.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.thuctaptotnghiep.Adapter.SliderAdapterExample2;
import com.example.thuctaptotnghiep.ChiTietSanPham;
import com.example.thuctaptotnghiep.Contect.SanPham;
import com.example.thuctaptotnghiep.Interface.IonClickSanPham;
import com.example.thuctaptotnghiep.R;
import com.example.thuctaptotnghiep.databinding.ActivityPhuKienBinding;
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
import java.util.prefs.Preferences;

public class PhuKien extends Fragment {
    ActivityPhuKienBinding binding;
    AdapterSanPham adapterTN ;
    AdapterSanPham adapterDP ;
    AdapterSanPham adapterCS ;
    List<String> list = new ArrayList<>();
    String TAG = "FIREBASE";
    List<SanPham> listTN;
    List<SanPham> listDP;
    List<SanPham> listCS;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SliderAdapterExample2 sliderAdapterExample;
    public static PhuKien newInstance() {
        Bundle args = new Bundle();
        PhuKien fragment = new PhuKien();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_phu_kien, container, false);
        listTN = new ArrayList<>();
        listDP = new ArrayList<>();
        listCS = new ArrayList<>();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = sharedPreferences.edit();
        sliderAdapterExample = new SliderAdapterExample2( getContext());
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
//Kết nối tới node có tên là contacts (node này do ta định nghĩa trong CSDL Firebase)
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
                    if( key.substring(0,2).equals("TN")){
                        SanPham sp = new SanPham(key,ten,thongTin,soLuong,donGia,NCC);
                        listTN.add(sp);
                    }
                    if( key.substring(0,2).equals("DP")){
                        SanPham sp = new SanPham(key,ten,thongTin,soLuong,donGia,NCC);
                        listDP.add(sp);
                    }
                    if( key.substring(0,2).equals("CS")){
                        SanPham sp = new SanPham(key,ten,thongTin,soLuong,donGia,NCC);
                        listCS.add(sp);
                    }

                }

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                binding.rvTaiNghe.setAdapter(adapterTN);
                binding.rvTaiNghe.setLayoutManager(layoutManager);
                RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                binding.rvCapSac.setAdapter(adapterCS);
                binding.rvCapSac.setLayoutManager(layoutManager1);
                RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                binding.rvDuPhong.setAdapter(adapterDP);
                binding.rvDuPhong.setLayoutManager(layoutManager2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });
        adapterCS = new AdapterSanPham(listCS);
        adapterTN = new AdapterSanPham(listTN);
        adapterDP = new AdapterSanPham(listDP);
        adapterCS.setClickSanPham(new IonClickSanPham() {
            @Override
            public void onClick(SanPham sanPham) {
                editor.putString("ma",sanPham.getMaSanPham());
                editor.putString("ten",sanPham.getTenSanPham());
                editor.putString("thongTin",sanPham.getThongTinSanPham());
                editor.putString("soLuong",sanPham.getSoLuong());
                editor.putString("donGia",sanPham.getDonGia());
                editor.commit();
                Intent intent = new Intent(getContext(), ChiTietSanPham.class);
                startActivity(intent);
            }
        });
        adapterDP.setClickSanPham(new IonClickSanPham() {
            @Override
            public void onClick(SanPham sanPham) {
                editor.putString("ma",sanPham.getMaSanPham());
                editor.putString("ten",sanPham.getTenSanPham());
                editor.putString("thongTin",sanPham.getThongTinSanPham());
                editor.putString("soLuong",sanPham.getSoLuong());
                editor.putString("donGia",sanPham.getDonGia());
                editor.commit();
                Intent intent = new Intent(getContext(), ChiTietSanPham.class);
                startActivity(intent);
            }
        });
        adapterTN.setClickSanPham(new IonClickSanPham() {
            @Override
            public void onClick(SanPham sanPham) {
                editor.putString("ma",sanPham.getMaSanPham());
                editor.putString("ten",sanPham.getTenSanPham());
                editor.putString("thongTin",sanPham.getThongTinSanPham());
                editor.putString("soLuong",sanPham.getSoLuong());
                editor.putString("donGia",sanPham.getDonGia());
                editor.commit();
                Intent intent = new Intent(getContext(), ChiTietSanPham.class);
                startActivity(intent);
            }
        });
        return binding.getRoot();
    }
}