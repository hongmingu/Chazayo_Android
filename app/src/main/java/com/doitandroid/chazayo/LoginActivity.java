package com.doitandroid.chazayo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setViews();

    }

    public void setViews(){
        findViewById(R.id.loginLoginButton).setOnClickListener(this);
        findViewById(R.id.loginSignUpButton).setOnClickListener(this);
        findViewById(R.id.loginFindPasswordButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginLoginButton:

                SharedPreferences sp = getSharedPreferences("sp_login", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("is_logged_in", "positive");
                editor.commit();

                finish();
                break;
            case R.id.loginSignUpButton:
                Intent intent = new Intent(getApplicationContext(), PhoneAuthActivity.class);
                startActivity(intent);
                break;
            case R.id.loginFindPasswordButton:
                break;
        }
    }
}
