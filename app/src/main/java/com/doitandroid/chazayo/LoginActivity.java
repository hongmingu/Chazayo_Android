package com.doitandroid.chazayo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {
    Button loginButton, signUpButton, pwFindButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginLoginButton);
        signUpButton = findViewById(R.id.loginSignUpButton);
        pwFindButton = findViewById(R.id.loginFindPasswordButton);

        //회원가입 버튼을 눌렀을 때 전화번호로 인증하러 보내는 메소드
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PhoneAuthActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp = getSharedPreferences("sp_login", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("is_logged_in", "positive");
                editor.commit();

                finish();
            }
        });
    }
}
