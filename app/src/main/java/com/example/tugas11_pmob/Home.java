package com.example.tugas11_pmob;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity{
    private SharedPreferences sharedPrefs;
    private Button btnLogout;
    private static final String AUTO_LOGIN_KEY = "key_auto_login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPrefs = getSharedPreferences("pmob224_sharedprefs", Context.MODE_PRIVATE);
        btnLogout = findViewById(R.id.btn_logout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.remove(AUTO_LOGIN_KEY);
                editor.apply();
                finish();
            }
        });
    }
}