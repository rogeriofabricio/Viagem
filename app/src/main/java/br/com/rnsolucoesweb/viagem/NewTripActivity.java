package br.com.rnsolucoesweb.viagem;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import br.com.rnsolucoesweb.viagem.models.Trip;
import br.com.rnsolucoesweb.viagem.models.User;

public class NewTripActivity extends BaseActivity {

    private static final String TAG = "NewTripActivity";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private EditText mOrigemField;
    private EditText mDestinoField;

    private FloatingActionButton mSubmitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        mOrigemField = findViewById(R.id.field_origem);
        mDestinoField = findViewById(R.id.field_destino);
        mSubmitButton = findViewById(R.id.fab_submit_trip);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitTrip();
            }
        });
    }

    private void submitTrip() {

        final String origem = mOrigemField.getText().toString();
        final String destino = mDestinoField.getText().toString();


        // Origem is required
        if (TextUtils.isEmpty(origem)) {
            mOrigemField.setError(REQUIRED);
            return;
        }

        // Destino is required
        if (TextUtils.isEmpty(destino)) {
            mDestinoField.setError(REQUIRED);
            return;
        }

        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Salvando...", Toast.LENGTH_SHORT).show();

        // [START single_value_read]
        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(NewTripActivity.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new trip
                            writeNewTrip(userId, user.username, origem, destino);
                        }

                        // Finish this Activity, back to the stream
                        setEditingEnabled(true);
                        finish();
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        setEditingEnabled(true);
                        // [END_EXCLUDE]
                    }
                });
        // [END single_value_read]
    }

    private void setEditingEnabled(boolean enabled) {

        mOrigemField.setEnabled(enabled);
        mDestinoField.setEnabled(enabled);


        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }

    // [START write_fan_out]
    private void writeNewTrip(String userId, String username, String origem, String destino) {
        // Create new trip at /user-trips/$userid/$tripid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("trips").push().getKey();
        Trip trip = new Trip(userId, username, origem, destino);
        Map<String, Object> tripValues = trip.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/trips/" + key, tripValues);
        childUpdates.put("/user-trips/" + userId + "/" + key, tripValues);

        mDatabase.updateChildren(childUpdates);
    }
    // [END write_fan_out]
}
