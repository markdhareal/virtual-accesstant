package com.app.sdchatbot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.sdchatbot.model.BookItem;
import com.app.sdchatbot.R;

import java.util.ArrayList;

public class BookItemAdapter extends RecyclerView.Adapter<BookItemAdapter.BookViewHolder> {

    ArrayList<BookItem> bookList;
    Context context;

    public BookItemAdapter(ArrayList<BookItem> bookList, Context context)
    {
        this.bookList = bookList;
        this.context = context;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.book_item_layout, parent, false);
        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        BookItem book = bookList.get(position);
        holder.keywordTextView.setText(book.getKeyword());
        holder.descriptionTextView.setText(book.getDescription());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder
    {

        TextView keywordTextView, descriptionTextView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            keywordTextView = itemView.findViewById(R.id.keywordText);
            descriptionTextView = itemView.findViewById(R.id.descriptionText);
        }
    }

}
