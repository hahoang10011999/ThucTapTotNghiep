package com.example.thuctaptotnghiep.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuctaptotnghiep.Contect.DonHang;
import com.example.thuctaptotnghiep.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class AdapterDatHang extends RecyclerView.Adapter<AdapterDatHang.ViewHolder> {
    Map<String, DonHang> donHangs;
    Context context;

    public AdapterDatHang(Map<String, DonHang> donHangs) {
        this.donHangs = donHangs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_dondathang,parent,false);
        AdapterDatHang.ViewHolder viewHolder = new AdapterDatHang.ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Set<String> set = donHangs.keySet();
        final List<String> keys = new ArrayList<>();
        keys.addAll(set);
        final DonHang donHang = donHangs.get(keys.get(position));
        String img = donHang.getTenSP()+".jpg";
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(img);
        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.img.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String str1 = currencyVN.format(Integer.parseInt(donHang.getDonGia()));

        String sl = String.valueOf(donHang.getSoLuong());
        String tongTien = currencyVN.format(donHang.getSoLuong()*Integer.parseInt(donHang.getDonGia()));
        holder.tvName.setText(donHang.getTenSP());
        holder.tvGia.setText("Đơn giá: " +str1);
        holder.tvSoLuong.setText("Số lượng: " + sl);
        holder.tvTongTien.setText("Thành tiền: "+tongTien);
    }

    @Override
    public int getItemCount() {
        return donHangs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvName,tvGia,tvSoLuong,tvTongTien;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tvName = itemView.findViewById(R.id.tvName);
            tvGia = itemView.findViewById(R.id.tvGia);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            tvTongTien = itemView.findViewById(R.id.tvTongTien);
        }
    }
}
