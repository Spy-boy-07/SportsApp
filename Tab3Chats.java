package com.example.varun.finalproject;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;

/**
 * Created by varun on 12-06-2017.
 */

public class Tab3Chats extends Fragment {



    private static int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseListAdapter<ChatMessage> adapter;
    RelativeLayout activity_chat;

    //Add Emojicon
    EmojiconEditText emojiconEditText;
    ImageView emojiButton,submitButton;
    EmojIconActions emojIconActions;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3chat, container, false);
        return rootView;

    }





    @Override
    public void onStart() {
        super.onStart();


            activity_chat = (RelativeLayout) getView().findViewById(R.id.tab3chat);

            //Add Emoji
            emojiButton = (ImageView) getView().findViewById(R.id.emoji_button);
            submitButton = (ImageView)getView().findViewById(R.id.submit_button);
            emojiconEditText = (EmojiconEditText) getView().findViewById(R.id.emojicon_edit_text);
            emojIconActions = new EmojIconActions(getActivity(),activity_chat,emojiButton,emojiconEditText);
            emojIconActions.ShowEmojicon();

            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseDatabase.getInstance().getReference("chat").push().setValue(new ChatMessage(emojiconEditText.getText().toString(),
                            FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                    emojiconEditText.setText("");
                    emojiconEditText.requestFocus();
                }
            });

            //Check if not sign-in then navigate Signin page
            if(FirebaseAuth.getInstance().getCurrentUser() == null)
            {
                startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),SIGN_IN_REQUEST_CODE);
            }
            else
            {
                Snackbar.make(activity_chat,"Welcome "+FirebaseAuth.getInstance().getCurrentUser().getEmail(),Snackbar.LENGTH_SHORT).show();
                //Load content
                displayChatMessage();
            }


        }



    private void displayChatMessage() {

        ListView listOfMessage = (ListView) getView().findViewById(R.id.list_of_message);
        adapter = new FirebaseListAdapter<ChatMessage>(getActivity(),ChatMessage.class,R.layout.chat_listitem,FirebaseDatabase.getInstance().getReference("chat"))
        {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {

                //Get references to the views of list_item.xml
                TextView messageText, messageUser, messageTime;
                messageText = (EmojiconTextView) v.findViewById(R.id.message_text);
                messageUser = (TextView) v.findViewById(R.id.message_user);
                messageTime = (TextView) v.findViewById(R.id.message_time);

                messageText.setText(model.getText());
                messageUser.setText(model.getUserName());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getTime()));

            }
        };
        listOfMessage.setAdapter(adapter);
    }

    }

