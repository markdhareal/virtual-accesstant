package com.app.sdchatbot.book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.app.sdchatbot.R;
import com.app.sdchatbot.adapter.BookItemAdapter;
import com.app.sdchatbot.model.BookItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BookItemAdapter bookItemAdapter;
    ArrayList<BookItem> bookItemsList;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        recyclerView = findViewById(R.id.recyclerViewBookOfKnowledge);
        databaseReference = FirebaseDatabase.getInstance().getReference("Keywords");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookItemsList = new ArrayList<>();
        BookItemAdapter bookItemAdapter = new BookItemAdapter(bookItemsList, this);
        recyclerView.setAdapter(bookItemAdapter);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    BookItem bookItem = dataSnapshot.getValue(BookItem.class);
                    bookItemsList.add(bookItem);
                }

                bookItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}