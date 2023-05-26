package com.app.sdchatbot.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.sdchatbot.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddReplyMainActivity extends AppCompatActivity {

    private EditText keywordReply, robotReply;
    private Button addReplyButton;

    DatabaseReference referenceReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reply_main);

        referenceReply = FirebaseDatabase.getInstance().getReference("WordBank");

        keywordReply = findViewById(R.id.replyId);
        robotReply = findViewById(R.id.replyDescriptionId);
        addReplyButton = findViewById(R.id.addButtonReply);

        addReplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keywordReplyString = keywordReply.getText().toString().trim();
                String robotReplyString = robotReply.getText().toString().trim();

                if(keywordReplyString.isEmpty())
                {
                    keywordReply.setError("Please input keyword");
                }
                else if(robotReplyString.isEmpty())
                {
                    robotReply.setError("Please input reply");
                }
                else
                {
                    addReplyData(keywordReplyString,robotReplyString);
                }

                keywordReply.setText("");
                robotReply.setText("");
            }
        });
    }

    private void addReplyData(String keywordReplyString, String robotReplyString)
    {
        referenceReply.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(keywordReplyString))
                {
                    Toast.makeText(AddReplyMainActivity.this, "Keyword already exist", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    referenceReply.child(keywordReplyString).child("reply").setValue(robotReplyString);
                    Toast.makeText(AddReplyMainActivity.this, "Reply added successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}