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

    private static final String TAG = "MainActivity";

    Toolbar toolbar;

    ArrayList<Fragment> fragmentArrayList, fragmentManagerArrayList;
    Fragment fragmentHome, fragmentHome2;
    FragmentManager fragmentManager;

    SingletonHolder singletonHolder = SingletonHolder.getInstance();
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

        if (fragment == null) {
            return;
        }

        for (Fragment fragmentItem : fragmentManager.getFragments()) {
            if (fragmentItem != null && fragmentItem.isVisible()) {
                fragmentManager.beginTransaction().hide(fragmentItem).commit();
            }
        }
        fragmentManager.beginTransaction().show(fragment).commit();
    }


    private void setFragments() {
        fragmentHome = new HomeFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_fragment_frame, fragmentHome).commit();
        showFragment(fragmentHome);
    }



    public void setAPIInterfaces(){
        singletonHolder.setFamilyAPIInterface(getAPIInterface());
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

    public APIInterface getAPIInterface() {
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        return apiInterface;
    }

}
