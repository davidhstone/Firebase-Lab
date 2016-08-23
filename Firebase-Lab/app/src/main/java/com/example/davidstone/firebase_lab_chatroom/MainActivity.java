package com.example.davidstone.firebase_lab_chatroom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView mCurrentText;
    EditText mEditText;
    Button mAddButton;
    ListView mListView;

    List<String> mMessages;

    ArrayAdapter<String> mAdapter;

    DatabaseReference dbRef;

    Random mRandomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.messages_listview);
        mEditText = (EditText) findViewById(R.id.edittext_edit);
        mAddButton = (Button) findViewById(R.id.add_button);

        mMessages = new ArrayList<>();

      //  Random randomId = new Random();
      //  int value = rand.nextInt(999) + 1;

        dbRef = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference messagesObject = dbRef.child("messages");

        //DatabaseReference firebaseMessageRef = dbRef.child("messages");


    //   mAddButton.setOnClickListener(new View.OnClickListener() {
    //       @Override
    //       public void onClick(View v) {
    //           dbRef.setValue(mEditText.getText().toString());
    //       }
    //   });

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mMessages);
        mListView.setAdapter(adapter);

        messagesObject.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String message = dataSnapshot.getValue(String.class);
                mMessages.add(message);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mAdapter =  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,mMessages);

        FirebaseListAdapter<String> firebaseAdapter =
                new FirebaseListAdapter<String>(this, String.class, android.R.layout.simple_list_item_1, messagesObject) {
                    @Override
                    protected void populateView(View v, String model, int position) {
                        TextView textView = (TextView) v.findViewById(android.R.id.text1);
                        textView.setText(model);
                    }
                };

        //  mListView.setAdapter(adapter);


        mListView.setAdapter(firebaseAdapter);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  objectRef.child("first_item").setValue(mEditText.getText().toString());

                messagesObject.push().setValue(mEditText.getText().toString());
            }
        });

    }


}
