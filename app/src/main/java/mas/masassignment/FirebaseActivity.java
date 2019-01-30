package mas.masassignment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FirebaseActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference("dates");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        final ListView dateList = findViewById(R.id.datesView);

// Attach a listener to read the data at our posts reference
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> dates = dataSnapshot.getChildren();
                ArrayList<String> loadedDates = new ArrayList<>();
                for (DataSnapshot date : dates) {
                    loadedDates.add(date.getValue(String.class));
                }
                adapter = new ArrayAdapter<String>(FirebaseActivity.this, R.layout.list_view, R.id.textView, loadedDates);
                dateList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Database", "The read failed: " + databaseError.getCode());
            }
        });
    }

}
