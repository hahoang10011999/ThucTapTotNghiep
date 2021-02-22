package com.example.thuctaptotnghiep;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.example.thuctaptotnghiep.Adapter.AdapterDatHang;
import com.example.thuctaptotnghiep.Adapter.AdapterDonHang;
import com.example.thuctaptotnghiep.Contect.DonHang;
import com.example.thuctaptotnghiep.R;
import com.example.thuctaptotnghiep.databinding.ActivityThanhToanBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ThanhToan extends AppCompatActivity {
ActivityThanhToanBinding binding;
DonHang donHang;
SharedPreferences sharedPreferences;
SharedPreferences.Editor editor;
Map<String,DonHang> listDH ;
AdapterDatHang adapterDatHang;
DatabaseReference mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_thanh_toan);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        editor = sharedPreferences.edit();
        mData = FirebaseDatabase.getInstance().getReference();
        listDH = new HashMap<>();
        String maSP = sharedPreferences.getString("maSP","");
        String ten = sharedPreferences.getString("ten","");
        int soLuong = sharedPreferences.getInt("soLuong",1);
        String donGia = sharedPreferences.getString("donGia","");
        String maTV = sharedPreferences.getString("maTV","");
        final String maDH = sharedPreferences.getString("maDH","");
        String tenNguoiNhan = String.valueOf(binding.tenNguoiNhan.getText());
        String sdt = String.valueOf(binding.sdt.getText());
        String diaChi = String.valueOf(binding.diaChi.getText());

        donHang = new DonHang(donGia,maSP,maTV,soLuong,ten,"chưa giao",tenNguoiNhan,sdt,diaChi);

        listDH.put(maDH,donHang);

        adapterDatHang = new AdapterDatHang(listDH);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getBaseContext(),1, RecyclerView.VERTICAL, false);
        binding.rvDonHang.setAdapter(adapterDatHang);
        binding.rvDonHang.setLayoutManager(layoutManager);


        //Tạo đối tượng
        AlertDialog.Builder b = new AlertDialog.Builder(this);
//Thiết lập tiêu đề
        b.setTitle("Xác nhận");
        b.setMessage("Xác nhận thanh toán?");
// Nút Ok
        b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String maSP = sharedPreferences.getString("maSP","");
                String ten = sharedPreferences.getString("ten","");
                int soLuong = sharedPreferences.getInt("soLuong",1);
                String donGia = sharedPreferences.getString("donGia","");
                String maTV = sharedPreferences.getString("maTV","");
                final String maDH = sharedPreferences.getString("maDH","");
                String tenNguoiNhan = String.valueOf(binding.tenNguoiNhan.getText());
                String sdt = String.valueOf(binding.sdt.getText());
                String diaChi = String.valueOf(binding.diaChi.getText());
                donHang = new DonHang(donGia,maSP,maTV,soLuong,ten,"chưa giao",tenNguoiNhan,sdt,diaChi);
                mData.child("DonHang").child(maDH).setValue(donHang);
                finish();
                mData.child("GioHang").child(maDH).removeValue();
            }
        });
//Nút Cancel
        b.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
//Tạo dialog
        final AlertDialog al = b.create();
        binding.btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                al.show();
            }
        });
    }
}