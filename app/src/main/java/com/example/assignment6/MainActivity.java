package com.example.assignment6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.assignment6.fragments.UserDetailFragment;
import com.example.assignment6.fragments.UserListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final UserListFragment userListFragment=new UserListFragment();

        FragmentManager fragmentManager=getSupportFragmentManager();
        final FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.user_list_layout,userListFragment);
        transaction.commit();

    }
}
