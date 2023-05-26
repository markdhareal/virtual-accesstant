package com.app.sdchatbot.book;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.sdchatbot.R;
import com.app.sdchatbot.book.BookActivity;

public class WordsFragment extends Fragment {

    private Button goToBookButton;

    public WordsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View goToBookView =  inflater.inflate(R.layout.fragment_words, container, false);

        goToBookButton = goToBookView.findViewById(R.id.takeALookBtnId);

        goToBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBook();
            }
        });

        return goToBookView;
    }

    private void goToBook()
    {
        Intent goToBookAct = new Intent(getActivity(), BookActivity.class);
        startActivity(goToBookAct);
    }
}