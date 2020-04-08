package com.example.assignment6.interfaces;

import com.example.assignment6.models.Post;
import com.example.assignment6.models.Root;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserInterface {
    @GET("users")
    Call<List<Root>> getUserData();

    @GET("posts")
    Call<List<Post>> getPosts(@Query("userId") int id);

}
