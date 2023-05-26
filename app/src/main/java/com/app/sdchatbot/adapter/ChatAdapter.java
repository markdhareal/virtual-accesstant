package com.app.sdchatbot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.sdchatbot.R;
import com.app.sdchatbot.model.ChatModel;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {

    private ArrayList<ChatModel> chatModelArrayList;
    private Context context;

    public ChatAdapter(ArrayList<ChatModel> chatModelArrayList, Context context) {
        this.chatModelArrayList = chatModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        switch (viewType)
        {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_convo_layout,parent,false);
                return new UserViewHolder(view);

            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_bot_convo_layout,parent,false);
                return new BotViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatModel chatModel = chatModelArrayList.get(position);

        if(holder.getItemViewType()==0)
        {
            UserViewHolder userViewHolder = (UserViewHolder) holder;
            userViewHolder.userMessageTextView.setText(chatModel.getMessage());
        }
        else
        {
            BotViewHolder botViewHolder = (BotViewHolder) holder;
            botViewHolder.botMessageTextView.setText(chatModel.getMessage());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return chatModelArrayList.get(position).getSender();
    }

    @Override
    public int getItemCount() {
        return chatModelArrayList.size();
    }

    //ViewHolder for user textview
    public static class UserViewHolder extends RecyclerView.ViewHolder
    {
        TextView userMessageTextView;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userMessageTextView = itemView.findViewById(R.id.userChatId);
        }
    }

    //ViewHolder for bot textview
    public static class BotViewHolder extends RecyclerView.ViewHolder
    {
        TextView botMessageTextView;
        public BotViewHolder(@NonNull View itemView) {
            super(itemView);
            botMessageTextView = itemView.findViewById(R.id.chatBotId);
        }
    }

}
