package com.example.assignment6.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.assignment6.R;
import com.example.assignment6.adapters.PostAdapter;
import com.example.assignment6.adapters.UserAdapter;
import com.example.assignment6.models.Root;

import java.util.ArrayList;
import java.util.List;

public class UserDetailFragment extends Fragment {
Context context;
Button showPosts;
List<Root> userdetails=new ArrayList<Root>();
TextView name,userName,email,city,zipCode;
int position;
int id;
Dialog dialog;
public UserDetailFragment() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_user_detail, container, false);
        showPosts=view.findViewById(R.id.btn_show_posts);
        name=view.findViewById(R.id.tv_user_detail_name);
        userName=view.findViewById(R.id.tv_user_username);
        email=view.findViewById(R.id.tv_user_detail_email);
        city=view.findViewById(R.id.tv_user_detail_city);
        zipCode=view.findViewById(R.id.tv_user_detail_zip);
        showPosts.setBackground(getActivity().getResources().getDrawable(R.drawable.button_style));

        Bundle bundle=getArguments();
        id=bundle.getInt("id");
        name.setText("Name:"+bundle.getString("name"));
        userName.setText("UserName:"+bundle.getString("username"));
        email.setText("Email:"+bundle.getString("email"));
        city.setText("City:"+bundle.getString("city"));
        zipCode.setText("Zip-Code:"+bundle.getString("zipcode"));

        //Bundle ultarnative code
        /* userdetails= UserAdapter.root;  make the userAdapter root array static for access
        position=UserAdapter.sendPosition;*/

        /*name.setText("Name:"+userdetails.get(position).getName());
        userName.setText("UserName:"+userdetails.get(position).getUsername());
        email.setText("Email:"+userdetails.get(position).getEmail());
        city.setText("City:"+userdetails.get(position).getAddress().getCity());
        zipCode.setText("Zip-Code:"+userdetails.get(position).getAddress().getZipcode());*/

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);

        showPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostDialogFragment pd=new PostDialogFragment();
                Bundle bundle1=new Bundle();
                bundle1.putInt("userid",id);
                pd.setArguments(bundle1);
                FragmentManager fm=getFragmentManager();
                pd.show(fm,"dialog");
            }
        });
        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }
}
