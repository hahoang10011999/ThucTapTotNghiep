package com.example.thuctaptotnghiep.Contect;

public class SanPham {
    String maSanPham,tenSanPham,thongTinSanPham,soLuong,DonGia,NCC;

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }


    public String getThongTinSanPham() {
        return thongTinSanPham;
    }

    public void setThongTinSanPham(String thongTinSanPham) {
        this.thongTinSanPham = thongTinSanPham;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getDonGia() {
        return DonGia;
    }

    public void setDonGia(String donGia) {
        DonGia = donGia;
    }

    public String getNCC() {
        return NCC;
    }

    public void setNCC(String NCC) {
        this.NCC = NCC;
    }
    public SanPham(){

    }
    public SanPham(String maSanPham, String tenSanPham, String thongTinSanPham, String soLuong, String donGia, String NCC) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.thongTinSanPham = thongTinSanPham;
        this.soLuong = soLuong;
        DonGia = donGia;
        this.NCC = NCC;
    }
    public SanPham( String tenSanPham, String thongTinSanPham, String soLuong, String donGia, String NCC) {
        this.tenSanPham = tenSanPham;
        this.thongTinSanPham = thongTinSanPham;
        this.soLuong = soLuong;
        DonGia = donGia;
        this.NCC = NCC;
    }
}
