package mas.masassignment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
    private TextView date;
    private String currDateString = "";
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference("dates");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button nextPage = findViewById(R.id.nextPageButton);
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(view.getContext(), FirebaseActivity.class);
                startActivity(intent);
            }
        });

        date = findViewById(R.id.dateTextView);
        CalendarView cv = findViewById(R.id.calendarView);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                final String dateString = (i1 + 1) + "/" + i2 + "/" + i;
                date.setText(dateString);
                currDateString = dateString;
            }
        });

        Button saveDateButton = findViewById(R.id.saveDateButton);
        saveDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fireBaseSaveData(currDateString);
            }
        });

    }

    protected void fireBaseSaveData(final String data) {
        if (data.length() != 0) {
            // Hacky way to check if there is a dateString
            final String id = mDatabase.push().getKey();
            mDatabase.child(id).setValue(data);
            Toast.makeText(this, "Saved in firebase", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Input a date before saving", Toast.LENGTH_LONG).show();
        }
    }




}
