package com.example.assignment6.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment6.R;
import com.example.assignment6.models.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyHolder> {
    Context context;
    public  List<Post> posts;

    public PostAdapter(Context context,List<Post> posts) {
        this.context=context;
        this.posts=posts;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_fragment_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
       holder.id.setText("Id:-"+posts.get(position).getId());
       holder.title.setText("Title:-"+posts.get(position).getTitle());
       holder.body.setText("Body:-"+posts.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
      TextView id,title,body;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
         id=itemView.findViewById(R.id.tv_dialog_id);
         title=itemView.findViewById(R.id.tv_dialog_title);
         body=itemView.findViewById(R.id.tv_dialog_body);
        }
    }
}
