package com.app.sdchatbot.chat;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.sdchatbot.R;
import com.app.sdchatbot.chat.ChatBotConversationActivity;

public class ChatBotFragment extends Fragment {

    private Button chatButton;

    public ChatBotFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View chatBotFrag =  inflater.inflate(R.layout.fragment_chat_bot, container, false);

        chatButton = chatBotFrag.findViewById(R.id.chatWithChatBotId);

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToConvo();
            }
        });

        return chatBotFrag;
    }

    private void goToConvo()
    {
        Intent convo = new Intent(getActivity(), ChatBotConversationActivity.class);
        startActivity(convo);
    }
}