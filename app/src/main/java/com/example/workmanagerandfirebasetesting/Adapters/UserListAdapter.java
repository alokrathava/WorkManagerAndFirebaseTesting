package com.example.workmanagerandfirebasetesting.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workmanagerandfirebasetesting.Interface.RecyclerUserListClickListener;
import com.example.workmanagerandfirebasetesting.Model.Users;
import com.example.workmanagerandfirebasetesting.databinding.LayoutUserListBinding;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {


    List<Users> list;
    Context context;

    private RecyclerUserListClickListener recyclerUserListClickListener;

    public UserListAdapter(List<Users> list, Context context, RecyclerUserListClickListener recyclerUserListClickListener) {
        this.list = list;
        this.context = context;
        this.recyclerUserListClickListener = recyclerUserListClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LayoutUserListBinding recyclerRowBinding = LayoutUserListBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final Users users = list.get(position);
        holder.bindView(users.getUserName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LayoutUserListBinding userListBinding;

        public ViewHolder(@NonNull LayoutUserListBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.userListBinding = recyclerRowBinding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerUserListClickListener.onClick(getAdapterPosition());
                }
            });
        }

        public void bindView(String title) {
            userListBinding.tvUsername.setText(title);
        }
    }


}
