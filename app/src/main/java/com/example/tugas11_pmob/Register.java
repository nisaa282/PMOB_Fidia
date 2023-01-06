package com.example.tugas11_pmob;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tugas11_pmob.data.AppDbProvider;
import com.example.tugas11_pmob.data.User;
import com.example.tugas11_pmob.data.UserDao;

import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {
    private EditText edtName;
    private EditText edtNim;
    private EditText edtPw;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.btn_register = this.findViewById(R.id.btn_register);
        this.edtName = this.findViewById(R.id.edt_name);
        this.edtNim = this.findViewById(R.id.edt_nim);
        this.edtPw = this.findViewById(R.id.edt_password);

        this.btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid = auth();
                if (valid) {
                    UserDao user = AppDbProvider.getInstance(getApplicationContext()).userDao();
                    user.insertAll(makeUser());
                    Toast.makeText(Register.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private User makeUser() {
        User u = new User();
        u.name = this.edtName.getText().toString().trim();
        u.nim = this.edtNim.getText().toString().trim();
        u.password = this.edtPw.getText().toString().trim();
        return u;
    }

    private boolean auth() {
        String currentNim = this.edtNim.getText().toString().trim();
        String currentPw = this.edtPw.getText().toString().trim();
        String currentName = this.edtName.getText().toString().trim();

        if (currentName.isEmpty())
            Toast.makeText(this, "Name is requiered!", Toast.LENGTH_SHORT).show();
        else if (currentNim.isEmpty())
            Toast.makeText(this, "NIM is requiered!", Toast.LENGTH_SHORT).show();
        else if (currentPw.isEmpty())
            Toast.makeText(this, "Password is requiered!", Toast.LENGTH_SHORT).show();
        else {
            UserDao user = AppDbProvider.getInstance(this).userDao();
            User currentUserData = user.findByNim(currentNim);
            if (currentUserData != null)
                Toast.makeText(this, "You have registered!", Toast.LENGTH_SHORT).show();
            else
                return true;
        }
        return false;
    }
}