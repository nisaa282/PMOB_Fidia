package com.example.tugas11_pmob;

import static com.example.tugas11_pmob.R.id.edt_nim;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tugas11_pmob.data.AppDbProvider;
import com.example.tugas11_pmob.data.User;
import com.example.tugas11_pmob.data.UserDao;

public class WelcomeBack extends AppCompatActivity {
    private static final String AUTO_LOGIN_KEY = "key_auto_login";

    private EditText edtNim;
    private EditText edtPw;
    private SharedPreferences sharedPrefs;
    private Button btnLogin;
    private Button btnRegister;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_back);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        edtNim = findViewById(R.id.edt_nim);
        edtPw = findViewById(R.id.edt_password);
        sharedPrefs = this.getSharedPreferences("pmob224_sharedprefs", Context.MODE_PRIVATE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid = auth();
                if (valid){
                    Intent i = new Intent(WelcomeBack.this, Home.class);
                    startActivity(i);
                    makeAutoLogin();
                    finish();
                }else{
                    Toast.makeText(WelcomeBack.this, "Invalid!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(WelcomeBack.this, Register.class);
                startActivity(i);
            }
        });
        autoLogin();
    }

    private boolean auth()
    {
        String currentNim = this.edtNim.getText().toString();
        String currentPw = this.edtPw.getText().toString();

        UserDao user = AppDbProvider.getInstance(this).userDao();
        currentUser = user.findByNimAndPassword(currentNim, currentPw);

        return currentUser!=null?true:false;
    }

    private void makeAutoLogin()
    {
        SharedPreferences.Editor editor = this.sharedPrefs.edit();
        editor.putBoolean(AUTO_LOGIN_KEY, true);
        editor.apply();
    }

    private void autoLogin()
    {
        boolean auto = this.sharedPrefs.getBoolean(AUTO_LOGIN_KEY, false);
        if (auto)
        {
            Intent i = new Intent(WelcomeBack.this, Home.class);
            startActivity(i);
        }
    }
}