package com.example.rustong_pu.citygym;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Message");
    //Fabian: there needs to be a child "Message" with some value in the Firebase DB

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readFromDB();

        Button btn_save = (Button) findViewById(R.id.main_btn);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeToDB();
            }
        });
    }

    public void writeToDB(){
        EditText edt = (EditText)findViewById(R.id.main_edittext);
        String textToSave = edt.getText().toString();
        myRef.setValue(textToSave);
    }

    public void readFromDB(){
        final TextView db_output = (TextView) findViewById(R.id.main_text2);
        ValueEventListener dbListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String db_text = dataSnapshot.getValue(String.class);
                db_output.setText( db_text );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        };
        myRef.addValueEventListener(dbListener);
    }

}
