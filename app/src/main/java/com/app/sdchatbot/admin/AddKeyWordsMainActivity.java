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

public class AddKeyWordsMainActivity extends AppCompatActivity {

    private EditText keyword,keywordDescription;
    private Button addKeyWordButton;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_key_words_main);

        reference = FirebaseDatabase.getInstance().getReference("Keywords");

        keyword = findViewById(R.id.keyWordId);
        keywordDescription = findViewById(R.id.keyWordDescriptionId);
        addKeyWordButton = findViewById(R.id.addButtonKeyWord);

        addKeyWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyWordString = keyword.getText().toString().trim();
                String keyWordDescriptionString = keywordDescription.getText().toString().trim();

                if(keyWordString.isEmpty())
                {
                    keyword.setError("Please input keyword");
                }
                else if(keyWordDescriptionString.isEmpty())
                {
                    keywordDescription.setError("Please input description");
                }
                else
                {
                    addKeyWordData(keyWordString, keyWordDescriptionString);
                }

                keyword.setText("");
                keywordDescription.setText("");
            }
        });
    }

    private void addKeyWordData(String keyWordString, String keyWordDescriptionString)
    {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(keyWordString))
                {
                    Toast.makeText(AddKeyWordsMainActivity.this, "Keyword already exist", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    reference.child(keyWordString).child("keyword").setValue(keyWordString);
                    reference.child(keyWordString).child("description").setValue(keyWordDescriptionString);
                    Toast.makeText(AddKeyWordsMainActivity.this, "Keyword added successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}