package com.doitandroid.chazayo;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.doitandroid.chazayo.adapter.FamilyAdapter;
import com.doitandroid.chazayo.item.UserItem;
import com.doitandroid.chazayo.rest.APIInterface;
import com.doitandroid.chazayo.util.SingletonHolder;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FamilyActivity extends BaseActivity implements View.OnClickListener {
    FamilyAdapter adapter;
    ArrayList<UserItem> searchedItemArrayList;

    RecyclerView recyclerView;
    AppCompatEditText searchBar;


    APIInterface apiInterface;


    SingletonHolder singletonHolder = SingletonHolder.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        setViews();
        setAdapter();

        setAPIInterface();

    }
    public void setAPIInterface(){
        apiInterface = singletonHolder.getFamilyAPIInterface();
    }

    public void setAdapter(){

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        // 어댑터를 연결시킨다.
        searchedItemArrayList = new ArrayList<>();
        adapter = new FamilyAdapter(searchedItemArrayList, getApplicationContext());

        // 리사이클러뷰에 연결한다.
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.setNestedScrollingEnabled(false);

        adapter.notifyDataSetChanged();
    }

    public void setViews(){

        recyclerView = findViewById(R.id.family_recyclerview);

        searchBar = findViewById(R.id.family_search_bar);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.family_search_button:

                searchUser(searchBar.getText().toString());

                break;
            default:
                break;
        }

    }

    public void searchUser(String searchWord){

        RequestBody requestSearchWord = RequestBody.create(MediaType.parse("multipart/form-data"), searchWord);

        Call<JsonObject> call = apiInterface.searchWord(requestSearchWord);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    if (jsonObject != null) {
                        int rc = jsonObject.get("rc").getAsInt();

                        if (rc != 222) {
                            Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                            call.cancel();
                            return;
                        }
                        // 접속 성공.

                    }

                } else {
                    Toast.makeText(getApplicationContext(), "not successful", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "onFailure", Toast.LENGTH_SHORT).show();
                call.cancel();

            }
        });
    }
}
