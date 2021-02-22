package com.example.thuctaptotnghiep.Contect;

public class DonHang {
    public String donGia,maSP,maTV,tenSP,trangThai,tenNguoiNhan,sdtNguoiNhan,diaChiNhan;
    public int soLuong;

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getTenNguoiNhan() {
        return tenNguoiNhan;
    }

    public void setTenNguoiNhan(String tenNguoiNhan) {
        this.tenNguoiNhan = tenNguoiNhan;
    }

    public String getSdtNguoiNhan() {
        return sdtNguoiNhan;
    }

    public void setSdtNguoiNhan(String sdtNguoiNhan) {
        this.sdtNguoiNhan = sdtNguoiNhan;
    }

    public String getDiaChiNhan() {
        return diaChiNhan;
    }

    public void setDiaChiNhan(String diaChiNhan) {
        this.diaChiNhan = diaChiNhan;
    }

    public String getDonGia() {
        return donGia;
    }

    public void setDonGia(String donGia) {
        this.donGia = donGia;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getMaTV() {
        return maTV;
    }

    public void setMaTV(String maTV) {
        this.maTV = maTV;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public DonHang(String donGia, String maSP, String maTV, int soLuong, String tenSP) {
        this.donGia = donGia;
        this.maSP = maSP;
        this.maTV = maTV;
        this.soLuong = soLuong;
        this.tenSP = tenSP;
    }
    public DonHang(String donGia, String maSP, String maTV, int soLuong, String tenSP,String trangThai,String tenNguoiNhan,String sdtNguoiNhan,String diaChiNhan) {
        this.donGia = donGia;
        this.maSP = maSP;
        this.maTV = maTV;
        this.soLuong = soLuong;
        this.tenSP = tenSP;
        this.trangThai = trangThai;
        this.tenNguoiNhan =tenNguoiNhan;
        this.sdtNguoiNhan =sdtNguoiNhan;
        this.diaChiNhan = diaChiNhan;
    }
    public DonHang() {
    }
}
