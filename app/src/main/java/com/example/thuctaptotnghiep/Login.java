package com.example.thuctaptotnghiep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
    Button btnDangNhap;
    TextInputLayout layoutTK, layoutMK;
    List<String> list;
    EditText edTK, edMK;
    TextView textView;
    String TAG = "FIREBASE";
    boolean check=true;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView imgBack;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        layoutMK = findViewById(R.id.layoutMK);
        layoutTK = findViewById(R.id.layoutTK);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        editor = sharedPreferences.edit();
        handler = new Handler();
        btnDangNhap = findViewById(R.id.btnDangNhap);
        edTK = findViewById(R.id.tk);
        edMK = findViewById(R.id.mk);
        imgBack = findViewById(R.id.imgBack);
        textView = findViewById(R.id.signUp);
        list = new ArrayList<>();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String tk = edTK.getText().toString();
                final String mk = edMK.getText().toString();
                if (tk.isEmpty()) {
                    layoutTK.setError("Vui lòng nhập tài khoản");
                    ShowError3s showError3s = new ShowError3s();
                    handler.postDelayed(showError3s, 3000);
                }
                if (mk.isEmpty()) {
                    layoutMK.setError("Vui lòng nhập mật khẩu");
                    ShowError3s showError3s = new ShowError3s();
                    handler.postDelayed(showError3s, 3000);
                }
                if (!tk.isEmpty() && !mk.isEmpty()) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
//Kết nối tới node có tên là contacts (node này do ta định nghĩa trong CSDL Firebase)
                    DatabaseReference myRef = database.getReference("ThanhVien");
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot data : snapshot.getChildren()) {
                                for (DataSnapshot data1 : data.getChildren()) {
                                    String value = data1.getValue().toString();
                                    list.add(value);
                                }
                                if (tk.equals(list.get(5)) && mk.equals(list.get(2))) {
                                    if (list.get(3).equals("TV")) {
                                        editor.putString("quyen", "TV");
                                    } else {
                                        editor.putString("quyen", "NV");
                                    }
                                    editor.putString("maTV", data.getKey());
                                    editor.putString("dangNhap", "true");
                                    editor.putString("tenTV", list.get(1));
                                    editor.putString("diaChi", list.get(0));
                                    editor.putString("sdt", list.get(4));
                                    editor.putString("tk", list.get(5));
                                    editor.putString("mk", list.get(2));
                                    editor.commit();
                                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    check = false;
                                }
                                list.clear();
                            }
                            if (check==true) {
                                Toast.makeText(getBaseContext(), "Tài khoản hoặc mật khẩu chưa chính xác!!", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w(TAG, "loadPost:onCancelled", error.toException());
                        }
                    });

                }


            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getBaseContext(), DangKy.class);
                startActivity(intent);
            }
        });
    }

    class ShowError3s implements Runnable {

        @Override
        public void run() {
            handler.postDelayed(this, 3000);
            layoutMK.setError(null);
            layoutTK.setError(null);
        }
    }
}