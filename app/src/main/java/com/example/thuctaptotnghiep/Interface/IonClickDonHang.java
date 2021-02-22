package com.example.thuctaptotnghiep.Interface;

import com.example.thuctaptotnghiep.Contect.DonHang;

public interface IonClickDonHang {
    void onClickThem(String key,DonHang donHang);
    void onClickBot(String key,DonHang donHang);
    void onClickXoa(String key,DonHang donHang,int position);
    void onClickThanhToan(String key,DonHang donHang);
}
