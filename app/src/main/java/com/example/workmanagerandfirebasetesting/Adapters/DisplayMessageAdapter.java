package com.example.workmanagerandfirebasetesting.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workmanagerandfirebasetesting.Interface.RecyclerUserListClickListener;
import com.example.workmanagerandfirebasetesting.Model.MessageModel;
import com.example.workmanagerandfirebasetesting.databinding.ChatItemLeftBinding;
import com.example.workmanagerandfirebasetesting.databinding.ChatItemRightBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class DisplayMessageAdapter extends RecyclerView.Adapter {


    List<MessageModel> list;
    Context context;

    private int left_side = 1;
    private int right_side = 0;


    private RecyclerUserListClickListener recyclerUserListClickListener;

    public DisplayMessageAdapter(List<MessageModel> list, Context context, RecyclerUserListClickListener recyclerUserListClickListener) {
        this.list = list;
        this.context = context;
        this.recyclerUserListClickListener = recyclerUserListClickListener;
    }


    @Override
    public int getItemViewType(int position) {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (list.get(position).getSender_id().equals(firebaseUser.getUid())) {

            return right_side;
        } else {
            return left_side;
        }

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        if (viewType == left_side) {
            ChatItemLeftBinding chatItemLeftBinding = ChatItemLeftBinding.inflate(layoutInflater, parent, false);
            return new LeftViewHolder(chatItemLeftBinding);
        } else {
            ChatItemRightBinding rightBinding = ChatItemRightBinding.inflate(layoutInflater, parent, false);
            return new RightViewHolder(rightBinding);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessageModel messageModel = list.get(position);

        if (getItemViewType(position) == left_side) {

            LeftViewHolder leftViewHolder = (LeftViewHolder) holder;
            leftViewHolder.leftBinding.tvShowMsg.setText(messageModel.getMessage());
        } else {
            RightViewHolder rightViewHolder = (RightViewHolder) holder;
            rightViewHolder.rightBinding.tvShowMsg.setText(messageModel.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class LeftViewHolder extends RecyclerView.ViewHolder {

        ChatItemLeftBinding leftBinding;

        public LeftViewHolder(@NonNull ChatItemLeftBinding leftBinding) {
            super(leftBinding.getRoot());
            this.leftBinding = leftBinding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerUserListClickListener.onClick(getAdapterPosition());
                }
            });
        }

    }

    class RightViewHolder extends RecyclerView.ViewHolder {

        ChatItemRightBinding rightBinding;

        public RightViewHolder(@NonNull ChatItemRightBinding rightBinding) {
            super(rightBinding.getRoot());
            this.rightBinding = rightBinding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerUserListClickListener.onClick(getAdapterPosition());
                }
            });
        }

    }

}
