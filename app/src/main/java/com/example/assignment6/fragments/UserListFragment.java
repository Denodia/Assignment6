package com.example.assignment6.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.assignment6.R;
import com.example.assignment6.WIFIInternetConnectionDetector;
import com.example.assignment6.adapters.UserAdapter;
import com.example.assignment6.interfaces.UserInterface;
import com.example.assignment6.models.Root;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UserListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    Context context;
    Boolean isConnectionExist = false;
    WIFIInternetConnectionDetector cd;
    UserInterface myInterface;
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    ProgressDialog progressDialog;
    RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout errorLayout;
    Button retry;
    List<Root> root;
    TextView error;
    public UserListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_user_list, container, false);
        recyclerView=view.findViewById(R.id.user_list_recyclerview);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        progressDialog = new ProgressDialog(context);
        error=view.findViewById(R.id.tv_user_list_error_msg);
        retry=view.findViewById(R.id.btn_user_list_fragment_retry);
        errorLayout=view.findViewById(R.id.error_layout);
        layoutManager= new LinearLayoutManager(context.getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        internetCheck();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myInterface = retrofit.create(UserInterface.class);
        swipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) this);
        checkData();

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                internetCheck();
                checkData();
            }
        });

        return view;
    }



    private void internetCheck() {
        cd = new WIFIInternetConnectionDetector(context);
        isConnectionExist = cd.checkMobileInternetConn();
        if (isConnectionExist) {
            recyclerView.setVisibility(View.VISIBLE);

        } else {
            error.setText("No Internet Connection");
        }
    }

    private void checkData() {
        swipeRefreshLayout.setRefreshing(false);
        internetCheck();
        progressDialog.setMessage("Fetching Data...Please Wait");
        progressDialog.show();
        Call<List<Root>> call = myInterface.getUserData();
        call.enqueue(new Callback<List<Root>>() {
            @Override
            public void onResponse(Call<List<Root>> call, Response<List<Root>> response) {
                if (!response.isSuccessful()) {
                    progressDialog.dismiss();
                        swipeRefreshLayout.setRefreshing(false);
                        recyclerView.setVisibility(View.GONE);
                        error.setText("Something went Wrong Try After some time");
                        errorLayout.setVisibility(View.VISIBLE);
                        return;
                }

                root = response.body();
                if (root != null) {
                    progressDialog.dismiss();
                    recyclerView.setVisibility(View.VISIBLE);
                    errorLayout.setVisibility(View.GONE);
                    userAdapter = new UserAdapter(context.getApplicationContext(),root);
                    recyclerView.setAdapter(userAdapter);
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Root>> call, Throwable t) {
                if(isConnectionExist==false){
                    progressDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                    recyclerView.setVisibility(View.GONE);
                    error.setText("No internet connection");
                    errorLayout.setVisibility(View.VISIBLE);
                }
                else {
                    progressDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                    recyclerView.setVisibility(View.GONE);
                    error.setText("Something goes Wrong Try After some time");
                    errorLayout.setVisibility(View.VISIBLE);
                    return;
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }


    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        checkData();
    }
}
