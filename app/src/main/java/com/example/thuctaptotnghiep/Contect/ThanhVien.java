package com.example.thuctaptotnghiep.Contect;

public class ThanhVien {
    public String hoTen,diaChi,tk,mk,quyenTruyCap,sdt;

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getTk() {
        return tk;
    }

    public void setTk(String tk) {
        this.tk = tk;
    }

    public String getMk() {
        return mk;
    }

    public void setMk(String mk) {
        this.mk = mk;
    }

    public String getQuyenTruyCap() {
        return quyenTruyCap;
    }

    public void setQuyenTruyCap(String quyenTruyCap) {
        this.quyenTruyCap = quyenTruyCap;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public ThanhVien() {
    }

    public ThanhVien(String hoTen, String diaChi, String tk, String mk, String quyenTruyCap, String sdt) {
        this.hoTen = hoTen;
        this.diaChi = diaChi;
        this.tk = tk;
        this.mk = mk;
        this.quyenTruyCap = quyenTruyCap;
        this.sdt = sdt;
    }
}
