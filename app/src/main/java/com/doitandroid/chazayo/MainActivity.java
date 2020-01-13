package com.doitandroid.chazayo;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.doitandroid.chazayo.fragment.HomeFragment;
import com.doitandroid.chazayo.rest.APIClient;
import com.doitandroid.chazayo.rest.APIInterface;
import com.doitandroid.chazayo.util.SingletonHolder;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    // BaseActivity 를 만들어 액티비티들이 공통으로 자주 쓸만한 함수를 정의한다.

    private static final String TAG = "MainActivity";

    // TAG 를 만들어서 Log 쓸 때 편리하게 쓰도록한다.

    Toolbar toolbar;

    Fragment fragmentHome;
    FragmentManager fragmentManager;

    SingletonHolder singletonHolder = SingletonHolder.getInstance();
    // 싱글톤 사용법. 자세한 것은 안드로이드 싱글톤 검색- 싱글톤 구현하는 여러가지 방법 중 많이 쓰이는 방법이라 하길래 씀.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setToolbar();

        setViews();

        setAPIInterfaces();

        setFragments();
    }


    private void showFragment(Fragment fragment){
        // 프래그먼트를 넣어서 그 프래그먼트를 화면에 보이게 하는 코드.
        if (fragment == null) {
            // 넣어준 fragment 가 없으면 취소한다.
            return;
        }

        for (Fragment fragmentItem : fragmentManager.getFragments()) {
            // fragmentManager 에서 다루고 있는 fragment를 포문으로 돌린다.

            if (fragmentItem != null && fragmentItem.isVisible()) {
                // 만약 나온 fragment 가 존재하면서 현재 show 되고 있는 상태면
                fragmentManager.beginTransaction().hide(fragmentItem).commit();
                // 그 프래그먼트를 hide 해 버린다.
            }
        }
        fragmentManager.beginTransaction().show(fragment).commit();

        // 그런다음 처음에 가져온 fragment 만 보이게 한다.
    }


    private void setFragments() {

        // 프래그먼트를 처음에 초기화해준다.
        fragmentHome = new HomeFragment();
        // fragmentHome 초기화
        fragmentManager = getSupportFragmentManager();
        // fragmentManager 가져옴
        FragmentTransaction fragmentTransaction;

        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.main_fragment_frame, fragmentHome).commit();
        // transaction 으로 add해줌.
        showFragment(fragmentHome);
        // 위에서 쓴 함수.
    }



    public void setAPIInterfaces(){
        // util/singletonHolder 를 보면 apiinterface를 주는 것이 있다. 메인 액티비티가 만들어질 때 하나 만들어서 쓰는 중인데
        // 문제가 된다면 다른 방식을 택해도 된다.
        singletonHolder.setFamilyAPIInterface(getAPIInterface());

        // getAPIInterface 함수는 BaseActivity 로 옮김 있음.
    }

    public void openFamilyActivity(){

        Intent intent = new Intent(this, FamilyActivity.class);
        startActivity(intent);
    }


    public void login_check(){

        SharedPreferences sp = getSharedPreferences("sp_login", MODE_PRIVATE);

        if (sp.getString("is_logged_in", "negative").equals("positive")) {
            Toast.makeText(getApplicationContext(), "로그인되어 있습니다.", Toast.LENGTH_SHORT).show();
            // not auto login
        } else {

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }


    }

    public void setToolbar(){
        toolbar = findViewById(R.id.toolbarAll);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    public void setViews(){
        findViewById(R.id.toolbarHome).setOnClickListener(this);
        findViewById(R.id.toolbarMessage).setOnClickListener(this);
        findViewById(R.id.toolbarNoti).setOnClickListener(this);
        findViewById(R.id.toolbarMy).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toolbarHome:
                showFragment(fragmentHome);
                break;
            case R.id.toolbarMy:
                login_check();

                break;

            case R.id.toolbarNoti:
                Toast.makeText(getApplicationContext(), "logout", Toast.LENGTH_SHORT).show();

                SharedPreferences sp = getSharedPreferences("sp_login", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("is_logged_in", "negative");
                editor.commit();
                break;
            case R.id.toolbarMessage:
                Toast.makeText(getApplicationContext(), "login", Toast.LENGTH_SHORT).show();

                SharedPreferences sp1 = getSharedPreferences("sp_login", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sp1.edit();
                editor1.putString("is_logged_in", "positive");
                editor1.commit();
                break;

            default:
                break;
        }
    }



}
