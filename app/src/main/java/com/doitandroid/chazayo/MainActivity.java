package com.doitandroid.chazayo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.doitandroid.chazayo.fragment.HomeFragment;
import com.doitandroid.chazayo.fragment.MessageFragment;
import com.doitandroid.chazayo.rest.APIClient;
import com.doitandroid.chazayo.rest.APIInterface;
import com.doitandroid.chazayo.util.SingletonHolder;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    Toolbar toolbar;
    CoordinatorLayout toolbar_my, toolbar_noti, toolbar_message;

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
        fragmentHome2 = new MessageFragment();
        fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction;
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.main_fragment_frame, fragmentHome).commit();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_fragment_frame, fragmentHome2).commit();

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
        toolbar = findViewById(R.id.toolbar_all_tb);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    public void setViews(){
        toolbar_my = findViewById(R.id.toolbar_my);
        toolbar_my.setOnClickListener(this);

        toolbar_noti = findViewById(R.id.toolbar_noti);
        toolbar_noti.setOnClickListener(this);

        toolbar_message = findViewById(R.id.toolbar_message);
        toolbar_message.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toolbar_my:
                login_check();

                break;

            case R.id.toolbar_noti:
                Toast.makeText(getApplicationContext(), "logout", Toast.LENGTH_SHORT).show();

                SharedPreferences sp = getSharedPreferences("sp_login", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("is_logged_in", "negative");
                editor.commit();
                showFragment(fragmentHome2);
                break;
            case R.id.toolbar_message:
                Toast.makeText(getApplicationContext(), "login", Toast.LENGTH_SHORT).show();

                SharedPreferences sp1 = getSharedPreferences("sp_login", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sp1.edit();
                editor1.putString("is_logged_in", "positive");
                editor1.commit();
                showFragment(fragmentHome);
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
