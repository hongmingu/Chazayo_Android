package com.doitandroid.chazayo.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.doitandroid.chazayo.MainActivity;
import com.doitandroid.chazayo.R;
import com.doitandroid.chazayo.item.UserItem;
import com.doitandroid.chazayo.rest.APIInterface;
import com.doitandroid.chazayo.rest.ConstantREST;
import com.doitandroid.chazayo.util.SingletonHolder;

import java.util.ArrayList;

public class FamilyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "FamilyAdapter";

    ArrayList<UserItem> searchedItemArrayList;
    Context context;
    APIInterface apiInterface;
    SingletonHolder singleton = SingletonHolder.getInstance();

    public FamilyAdapter(ArrayList<UserItem> searchedItemArrayList, Context context) {
        this.searchedItemArrayList = searchedItemArrayList;
        this.context = context;
        apiInterface = singleton.getFamilyAPIInterface();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RecyclerView.ViewHolder viewHolder = null;
        View view;
        switch (viewType){
            case 1:
                view = inflater.inflate(R.layout.item_default_user, parent, false);
                viewHolder = new DefaultUserViewHolder(view);
                break;
            default:
                break;
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case 1:
                final UserItem userItem= searchedItemArrayList.get(position);

                break;
            default:
                break;
        }
    }



    @Override
    public int getItemCount() {
        return searchedItemArrayList.size();
    }

    private class DefaultUserViewHolder extends RecyclerView.ViewHolder {
        LinearLayoutCompat dpi_ping_wrapper_ll;
        public DefaultUserViewHolder(View view) {
            super(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
//        return searchedItemArrayList.get(position).getOpt();
        return 1;
    }

    @Override
    public long getItemId(int position) {
//        return super.getItemId(position);

        return position;
    }


}
