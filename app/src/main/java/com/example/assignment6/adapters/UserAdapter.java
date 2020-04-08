package com.example.assignment6.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment6.R;
import com.example.assignment6.fragments.UserDetailFragment;
import com.example.assignment6.models.Root;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ProgrammingViewHolder> {
    Context context;
    public static List<Root> root;
    public static int sendPosition;
    int id;

    public UserAdapter(Context context, List<Root> root) {
        this.context=context;
        this.root=root;
    }

    @NonNull
    @Override
    public ProgrammingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.user_list_item,parent,false);
        return new ProgrammingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProgrammingViewHolder holder, final int position) {
       holder.userName.setText("Name:"+root.get(position).getName());
       holder.id.setText("Id:"+root.get(position).getId());

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               /*sendPosition=position;
               id=root.get(position).getId();*/
               UserDetailFragment userDetailFragment = new UserDetailFragment();
               int userid=root.get(position).getId();
               String name=root.get(position).getName();
               String username=root.get(position).getUsername();
               String email=root.get(position).getEmail();
               String city=root.get(position).getAddress().getCity();
               String zipcode=root.get(position).getAddress().getZipcode();
               Bundle bundle=new Bundle();
               bundle.putInt("id",userid);
               bundle.putString("name",name);
               bundle.putString("username",username);
               bundle.putString("email",email);
               bundle.putString("city",city);
               bundle.putString("zipcode",zipcode);
               userDetailFragment.setArguments(bundle);

               AppCompatActivity activity = (AppCompatActivity) v.getContext();

               FragmentManager fragmentManager1=activity.getSupportFragmentManager();
               final FragmentTransaction transaction1=fragmentManager1.beginTransaction();
               transaction1.replace(R.id.user_detail_layout,userDetailFragment);
               transaction1.commit();
           }
       });
    }

    @Override
    public int getItemCount() {
        return root.size();
    }

    public class ProgrammingViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView id;

        public ProgrammingViewHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.tv_list_user_name);
            id=itemView.findViewById(R.id.tv_list_id);
        }
    }
}
