package com.example.thuctaptotnghiep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.thuctaptotnghiep.Adapter.SliderAdapterExample;
import com.example.thuctaptotnghiep.Fragment.DienThoai;
import com.example.thuctaptotnghiep.Fragment.GioHang;
import com.example.thuctaptotnghiep.Fragment.PhuKien;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    ImageView gio;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        gio = findViewById(R.id.imgGioHang);



        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        editor = sharedPreferences.edit();
        bottomNavigationView = findViewById(R.id.bottom_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.bottomDienThoai:
                        getFragment(new DienThoai());
                        break;
                    case R.id.bottomPhuKien:
                        getFragment(new PhuKien());
                        break;
                    case R.id.bottomCaNhan:
                        if(sharedPreferences.getString("dangNhap","").equals("true")){
                            finish();
                            Intent intent = new Intent(getBaseContext(),Profile.class);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(getBaseContext(),Login.class);
                            startActivity(intent);
                        }
                        break;
                }
                return true;
            }
        });

        gio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sharedPreferences.getString("dangNhap","").equals("true")&& sharedPreferences.getString("quyen","").equals("TV")){
                    getFragment(new GioHang());
                }else if(sharedPreferences.getString("dangNhap","").equals("true") && sharedPreferences.getString("quyen","").equals("NV")){
                    finish();
                    Intent intent = new Intent(getBaseContext(),DanhSachDonHang.class);
                    startActivity(intent);
                }else {
                    finish();
                    Intent intent = new Intent(getBaseContext(),Login.class);
                    startActivity(intent);
                }
            }
        });
        getFragment(DienThoai.newInstance());

    }
    public void getFragment(Fragment fragment) {
        try {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame, fragment)
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "getFragment: " + e.getMessage());
        }
    }
}