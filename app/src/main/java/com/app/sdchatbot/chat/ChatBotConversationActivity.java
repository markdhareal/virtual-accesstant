package com.app.sdchatbot.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.app.sdchatbot.R;
import com.app.sdchatbot.util.SharedPref;
import com.app.sdchatbot.adapter.ChatAdapter;
import com.app.sdchatbot.model.ChatModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ChatBotConversationActivity extends AppCompatActivity {

    private EditText editTextMessage;
    private ImageView sendButtonImageView;
    private RecyclerView messagesRecyclerView;
    private final int USER_KEY = 0;
    private final int BOT_KEY = 1;
    private ArrayList<ChatModel> arrayListChatModel;
    private ChatAdapter chatAdapter;

    DatabaseReference rootReference;

    FirebaseUser user;
    private String userID;
    private final String GREETING = "Hi I am RAM your robosistant, I can only " +
                              " provide limited information with regards to Computer Engineering" +
                              " program like schedules, subjects, general information, organizations" +
                              " and latest update in the school. How can I help you?";

    private final String PARDON = "Pardon, I find your question way too odd! Can you be more specific?";
    private final String SORRY = "Im sorry, but I can only handle few things.";
    private final String SPECIFIC = "Sorry, please be more specific.";
    private String[] noWord = {PARDON,SORRY,SPECIFIC};
    Random randomReply;

    LottieAnimationView lottieTyping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot_conversation);

        editTextMessage = findViewById(R.id.convoEditText);
        sendButtonImageView = findViewById(R.id.sendButtonImageViewId);
        messagesRecyclerView = findViewById(R.id.convoRecyclerview);
        arrayListChatModel = new ArrayList<>();
        chatAdapter = new ChatAdapter(arrayListChatModel,this);

        randomReply = new Random();

        //initializing lottie animation
        lottieTyping = findViewById(R.id.lottieTypingEffect);

        //for root reference
        rootReference = FirebaseDatabase.getInstance().getReference();

        //to get the current user
        user = FirebaseAuth.getInstance().getCurrentUser();

        //greeting of bot
        botTyping(GREETING);

        //send the messsage
        sendButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndSend();
                editTextMessage.setText("");
            }
        });

        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        messagesRecyclerView.setLayoutManager(linearLayoutManager);
        messagesRecyclerView.setAdapter(chatAdapter);

    }

    //method to check the message if it is empty or not
    private void checkAndSend()
    {
        String wholeMessage = editTextMessage.getText().toString().trim();
        userID = user.getUid();

        if(!wholeMessage.isEmpty())
        {
            arrayListChatModel.add(new ChatModel(wholeMessage,USER_KEY));
            chatAdapter.notifyDataSetChanged();
            sendAndGetResponse(wholeMessage,userID);
        }
        else
        {
            Toast.makeText(ChatBotConversationActivity.this, "Please input message", Toast.LENGTH_SHORT).show();
        }
    }


    //method to send the message
    private void sendAndGetResponse(String wholeMessage, String userId)
    {
        DatabaseReference userMessagesKey = rootReference.child("Messages").child(userId).push();
        String messagePushID = userMessagesKey.getKey();

        Map wholeMessageInfo = new HashMap();
        wholeMessageInfo.put("sender", userId);
        wholeMessageInfo.put("message", wholeMessage);

        Map messageInfo = new HashMap();
        messageInfo.put(userId+ "/" +messagePushID, wholeMessageInfo);


        //get reference from the word bank
        rootReference = FirebaseDatabase.getInstance().getReference("WordBank");

        //read the text message from the user and check if it exists
        rootReference.child(wholeMessage).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful())
                {
                    if (task.getResult().exists())
                    {
                        DataSnapshot dataSnapshot = task.getResult();
                        String reply = String.valueOf(dataSnapshot.child("reply").getValue());
                        botTyping(reply);
                        String savedFile = "";
                        switch (wholeMessage)
                        {
                            case "first":
                                savedFile = "1ST-YEAR-SUBJECT-SCHEDULE";
                                break;

                            case "second":
                                savedFile = "2ND-YEAR-SUBJECT-SCHEDULE";
                                break;

                            case "third":
                                savedFile = "3RD-YEAR-SUBJECT-SCHEDULE";
                                break;

                            case "fourth":
                                savedFile = "4TH-YEAR-SUBJECT-SCHEDULE";
                                break;

                            case "IDE":
                                savedFile = "IDE's";
                                break;

                            case "prospectus":
                                savedFile = "Prospectus";
                                break;

                            default:
                                savedFile = "No Files in here.";
                                break;
                        }

                        SharedPref.saveFileName(getApplicationContext(), savedFile);
                    }
                    else
                    {
                        //to randomize the robot reply
                        int robotRandomReply = randomReply.nextInt(noWord.length);
                        botTyping(noWord[robotRandomReply]);
                    }
                }
            }
        });

    }

    //method for bot typing and reply
    private void botTyping(String botReply)
    {
        lottieTyping.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lottieTyping.setVisibility(View.GONE);
                arrayListChatModel.add(new ChatModel(botReply, BOT_KEY));
                chatAdapter.notifyDataSetChanged();
            }
        },3000);
    }
}