package com.app.sdchatbot.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.app.sdchatbot.chat.ChatBotFragment;
import com.app.sdchatbot.download.DownloadFileFragment;
import com.app.sdchatbot.R;
import com.app.sdchatbot.book.WordsFragment;
import com.app.sdchatbot.admin.AddKeyWordsMainActivity;
import com.app.sdchatbot.admin.AddReplyMainActivity;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivityChatBot extends AppCompatActivity {

    MeowBottomNavigation meowBottomNavigation;
    Toolbar toolbar;
    private FirebaseAuth firebaseAuthMainActChatBot;

    TextView exit, exitAddReply;
    ImageView imageAddKeyWords, imageAddReply;
    EditText editTextAddKeyWords, editTextAddReply;
    Button buttonLogInAddKeyWords, buttonLogInAddReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat_bot);

        firebaseAuthMainActChatBot = FirebaseAuth.getInstance();

        //toolbar
        toolbar = findViewById(R.id.toolbarId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        //assigning meow bottom nav
        meowBottomNavigation = findViewById(R.id.meowBottomNavID);

        //Adding menu items and icons
        meowBottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.files_bottom_nav_icon));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.chat_bottom_nav_icon));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.words_bottom_nav_icon));


        meowBottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                int id = item.getId();
                switch (id) {
                    case 1:
                        toastSomething("Download");
                        break;

                    case 2:
                        toastSomething("RAM");
                        break;

                    case 3:
                        toastSomething("Book");
                        break;
                }
            }
        });

        meowBottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                int id = item.getId();
                switch (id) {
                    case 1:
                        toastSomething("Download");
                        break;

                    case 2:
                        toastSomething("RAM");
                        break;

                    case 3:
                        toastSomething("Book");
                        break;
                }
            }
        });


        meowBottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                //Initializing Fragment
                Fragment fragment = null;

                //Get Id
                int id = item.getId();

                //to select the id's
                //to go in another fragments
                switch (id) {
                    case 1:
                        //will go to download files fragment
                        fragment = new DownloadFileFragment();
                        break;

                    case 2:
                        //will go to chat bot fragment
                        fragment = new ChatBotFragment();
                        break;

                    case 3:
                        //will go to words fragment
                        fragment = new WordsFragment();
                        break;
                }

                //will load to the chosen fragment
                goToFragment(fragment);
            }
        });


        //set chat bot fragment as initially selected
        meowBottomNavigation.show(2, true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id)
        {
            case R.id.addKeyWords:
                addKeyWords();
                break;

            case R.id.addReplies:
                addReply();
                break;

            case R.id.logOutId:
                //Logout Code
                logOut();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Method to load the fragment
    private void goToFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }

    //Method for Toast in Meow Bottom Nav
    private void toastSomething(String text)
    {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    //to log out the user
    private void logOut()
    {
        firebaseAuthMainActChatBot.signOut();
        startActivity(new Intent(MainActivityChatBot.this, MainActivity.class));
    }

    //to show pop up add keywords
    private void addKeyWords()
    {

        AlertDialog.Builder keyWordAlert = new AlertDialog.Builder(MainActivityChatBot.this, R.style.AlertDialogTheme);

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_add_key_words, null);

        exit = view.findViewById(R.id.exitAddKeyWordsId);
        imageAddKeyWords = view.findViewById(R.id.imageAddKeyWordsId);
        editTextAddKeyWords = view.findViewById(R.id.editTextAddKeyWordsId);
        buttonLogInAddKeyWords = view.findViewById(R.id.logInButtonAddKeyWordsId);

        keyWordAlert.setView(view);
        keyWordAlert.setCancelable(false);



        AlertDialog keyWordDialog = keyWordAlert.create();
        Window window = keyWordDialog.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setBackgroundDrawable(new ColorDrawable(0));
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        keyWordDialog.show();

        //verifying user password
        buttonLogInAddKeyWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String passwordKeywords = editTextAddKeyWords.getText().toString().trim();

                if (!passwordKeywords.isEmpty())
                {
                    if(passwordKeywords.equals("ram"))
                    {
                        startActivity(new Intent(MainActivityChatBot.this, AddKeyWordsMainActivity.class));
                        keyWordDialog.dismiss();
                    }
                    else
                    {
                        editTextAddKeyWords.setError("Incorrect Password");
                    }
                }
                else
                {
                    Toast.makeText(MainActivityChatBot.this, "Please input your password.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyWordDialog.dismiss();
            }
        });

    }

    //to show pop up add reply
    private void addReply()
    {
        AlertDialog.Builder replyAlert = new AlertDialog.Builder(MainActivityChatBot.this, R.style.AlertDialogTheme);
        LayoutInflater layoutInflater = getLayoutInflater();

        View v = layoutInflater.inflate(R.layout.activity_add_replies, null);

        exitAddReply = v.findViewById(R.id.exitAddReplyId);
        imageAddReply = v.findViewById(R.id.imageAddReplyId);
        editTextAddReply = v.findViewById(R.id.editTextAddReplyId);
        buttonLogInAddReply = v.findViewById(R.id.logInButtonAddReplyId);

        replyAlert.setView(v);
        replyAlert.setCancelable(false);


        AlertDialog replyDialog = replyAlert.create();
        Window windowReply = replyDialog.getWindow();
        windowReply.requestFeature(Window.FEATURE_NO_TITLE);
        windowReply.setBackgroundDrawable(new ColorDrawable(0));
        windowReply.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        replyDialog.show();

        //verifying user password
        buttonLogInAddReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String passwordReply = editTextAddReply.getText().toString().trim();

                if (!passwordReply.isEmpty())
                {
                    if(passwordReply.equals("ram"))
                    {
                        startActivity(new Intent(MainActivityChatBot.this, AddReplyMainActivity.class));
                        replyDialog.dismiss();
                    }
                    else
                    {
                        editTextAddReply.setError("Incorrect Password");
                    }
                }
                else
                {
                    Toast.makeText(MainActivityChatBot.this, "Please input your password.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        exitAddReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replyDialog.dismiss();
            }
        });

    }
}