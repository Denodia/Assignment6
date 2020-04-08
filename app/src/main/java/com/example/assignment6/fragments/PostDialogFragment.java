package com.example.assignment6.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.assignment6.R;
import com.example.assignment6.WIFIInternetConnectionDetector;
import com.example.assignment6.adapters.PostAdapter;
import com.example.assignment6.adapters.UserAdapter;
import com.example.assignment6.interfaces.UserInterface;
import com.example.assignment6.models.Post;
import com.example.assignment6.models.Root;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PostDialogFragment extends DialogFragment implements SwipeRefreshLayout.OnRefreshListener {
    Context context;
    Boolean isConnectionExist = false;
    WIFIInternetConnectionDetector cd;
    UserInterface myInterface;
    RecyclerView recyclerView;
    PostAdapter postAdapter;
    ProgressDialog progressDialog;
    RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout errorLayout;
    Dialog dialog;
    Button retry;
    TextView error,crossButton;
    int id;
    public PostDialogFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_post_dialog, container, false);
        recyclerView=view.findViewById(R.id.dialog_recycler_view);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.dialog_swipe_container);
        progressDialog = new ProgressDialog(context);
        crossButton=view.findViewById(R.id.tv_cross_button);
        error=view.findViewById(R.id.tv_dialog_error);
        retry=view.findViewById(R.id.btn_dialog_retry);
        errorLayout=view.findViewById(R.id.dialog_error_layout);
        layoutManager= new LinearLayoutManager(context.getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        Bundle bundle=getArguments();
        id=bundle.getInt("userid");

        internetCheck();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myInterface = retrofit.create(UserInterface.class);
        swipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) this);
        getPosts();

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                internetCheck();
                getPosts();
            }
        });

        crossButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             dismiss();
            }
        });

        return view;
    }

    private void  getPosts() {
        swipeRefreshLayout.setRefreshing(false);
        internetCheck();
        progressDialog.setMessage("Fetching Data...Please Wait");
        progressDialog.show();
        Call<List<Post>> call = myInterface.getPosts(id);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    progressDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                    recyclerView.setVisibility(View.GONE);
                    error.setText("Something went Wrong Try After some time");
                    errorLayout.setVisibility(View.VISIBLE);
                    return;
                }

                List<Post>  posts = response.body();
                if (posts != null) {
                    progressDialog.dismiss();
                    recyclerView.setVisibility(View.VISIBLE);
                    errorLayout.setVisibility(View.GONE);
                    postAdapter = new PostAdapter(context.getApplicationContext(),posts);
                    recyclerView.setAdapter(postAdapter);

                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
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

    private void internetCheck() {
        cd = new WIFIInternetConnectionDetector(context);
        isConnectionExist = cd.checkMobileInternetConn();
        if (isConnectionExist) {
            recyclerView.setVisibility(View.VISIBLE);

        } else {
            error.setText("No Internet Connection");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        getPosts();
    }

    @Override
    public void onStart() {
        super.onStart();
        dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
