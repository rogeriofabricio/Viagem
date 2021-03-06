package br.com.rnsolucoesweb.viagem;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.rnsolucoesweb.viagem.models.Trip;
import br.com.rnsolucoesweb.viagem.models.User;

public class NewTripActivity extends BaseActivity implements View.OnClickListener {

    private Button btDeparture;
    private Button btArrival;
    private TextView tvDeparture;
    private TextView tvArrival;
    Context context = this;

    private static final String TAG = "NewTripActivity";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    public String mDepartureR;
    public String mArrivalR;

    private TextView mDepartureField;
    private TextView mArrivalField;
    public TextView mDateField;
    public Button mDateButton;

    //Date datePicker
    public String mTripDate;
    public String mTripDateBR;

    static final int DATE_DIALOG_ID = 0;

    private FloatingActionButton mSubmitButton;

    SimpleDateFormat dateNow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);

        //button's reference
        btDeparture = findViewById(R.id.btDeparture);
        btArrival = findViewById(R.id.btArrival);
        tvDeparture = findViewById(R.id.tv_bt_departure);
        tvArrival = findViewById(R.id.tv_bt_arrival);

        //setting button's click and implementing the onClick method
        btDeparture.setOnClickListener(new View.OnClickListener() {

            @Override  public void onClick(View v) {
                //List of items to be show in  alert Dialog are stored in array of strings/char sequences  final
                final String[] items = {"Recife", "Gravatá", "Bezerros", "Caruaru", "Belo Jardim", "Pesqueira", "Arcoverde", "Algodões", "Custódia", "Sítio dos Nunes", "Serra Talhada"};

                AlertDialog.Builder builderDeparture = new AlertDialog.Builder(context);

                //set the title for alert dialog
                builderDeparture.setTitle("PARTIDA: ");

                //set items to alert dialog. i.e. our array , which will be shown as list view in alert dialog
                builderDeparture.setItems(items, new DialogInterface.OnClickListener() {

                    @Override public void onClick(DialogInterface dialog, int item) {
                        //setting the button text to the selected itenm from the list
                        tvDeparture.setText(items[item]);
                    }
                });

                //Creating CANCEL button in alert dialog, to dismiss the dialog box when nothing is selected
                builderDeparture .setCancelable(false)
                        .setNegativeButton("CANCEL",new DialogInterface.OnClickListener() {

                            @Override  public void onClick(DialogInterface dialog, int id) {
                                //When clicked on CANCEL button the dalog will be dismissed
                                dialog.dismiss();
                            }
                        });

                //Creating alert dialog
                AlertDialog alertDeparture =builderDeparture.create();

                //Showingalert dialog
                alertDeparture.show();

            }
        });

        //setting button's click and implementing the onClick method
        btArrival.setOnClickListener(new View.OnClickListener() {

            @Override  public void onClick(View v) {
                //List of items to be show in  alert Dialog are stored in array of strings/char sequences  final
                final String[] items = {"Recife", "Gravatá", "Bezerros", "Caruaru", "Belo Jardim", "Pesqueira", "Arcoverde", "Algodões", "Custódia", "Sítio dos Nunes", "Serra Talhada"};

                AlertDialog.Builder builderArrival = new AlertDialog.Builder(context);

                //set the title for alert dialog
                builderArrival.setTitle("DESTINO: ");

                //set items to alert dialog. i.e. our array , which will be shown as list view in alert dialog
                builderArrival.setItems(items, new DialogInterface.OnClickListener() {

                    @Override public void onClick(DialogInterface dialog, int item) {
                        //setting the button text to the selected itenm from the list
                        tvArrival.setText(items[item]);
                    }
                });

                //Creating CANCEL button in alert dialog, to dismiss the dialog box when nothing is selected
                builderArrival .setCancelable(false)
                        .setNegativeButton("CANCEL",new DialogInterface.OnClickListener() {

                            @Override  public void onClick(DialogInterface dialog, int id) {
                                //When clicked on CANCEL button the dalog will be dismissed
                                dialog.dismiss();
                            }
                        });

                //Creating alert dialog
                AlertDialog alertArrival = builderArrival.create();

                //Showingalert dialog
                alertArrival.show();

            }
        });

        // Get the Intent that started this activity and extract the string
//        Bundle arrival = getIntent().getExtras();
//        mDepartureR = arrival.getString("departure");
//        mArrivalR = arrival.getString("arrival");

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        mDepartureField = tvDeparture;
//        mDepartureField.setText(mDepartureR);
        mArrivalField = tvArrival;
//        mArrivalField.setText(mArrivalR);
        mSubmitButton = findViewById(R.id.fab_submit_trip);

        mDateButton = findViewById(R.id.button_date);
        mDateButton.setOnClickListener(this);

        mDateField = findViewById(R.id.field_date);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitTrip();
            }
        });
    }

    private void submitTrip() {

        final String departure = mDepartureField.getText().toString();
        final String arrival = mArrivalField.getText().toString();
        final String date = mDateField.getText().toString();
        
        String partida = "PARTIDA";
        String destino = "DESTINO";
        String data = "DATA DA VIAGEM";

        // Departure is required
        if (TextUtils.equals(departure, partida)) {
            mDepartureField.setError(REQUIRED);
            return;
        }

        // Arrival is required
        if (TextUtils.equals(arrival, destino)) {
            mArrivalField.setError(REQUIRED);
            return;
        }

        // Date is required
        if (TextUtils.equals(date, data)) {
            mDateField.setError(REQUIRED);
            return;
        }



        // Disable button so there are no multi-posts
//        setEditingEnabled(false);
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
                            writeNewTrip(userId, user.username, departure, arrival, date);
                        }

                        // Finish this Activity, back to the stream
//                        setEditingEnabled(true);
                        GoToMainActivity();
                        finish();
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
//                        setEditingEnabled(true);
                        GoToMainActivity();
                        // [END_EXCLUDE]
                    }
                });
        // [END single_value_read]
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, year, month,
                        day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {

                    if (monthOfYear < 10 && dayOfMonth < 10) {
                        mTripDate = String.valueOf(year) + "0" + String.valueOf(monthOfYear+1) + "0" + String.valueOf(dayOfMonth);
                    } else if (monthOfYear < 10 && dayOfMonth > 9) {
                        mTripDate = String.valueOf(year) + "0" + String.valueOf(monthOfYear+1) + String.valueOf(dayOfMonth);
                    } else if (monthOfYear > 9 && dayOfMonth < 10){
                        mTripDate = String.valueOf(year) + String.valueOf(monthOfYear+1) + "0" + String.valueOf(dayOfMonth);
                    } else {
                        mTripDate = String.valueOf(year) + String.valueOf(monthOfYear+1) + String.valueOf(dayOfMonth);
                    }

                    mTripDateBR = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear+1) + "/" + String.valueOf(year);
                    mDateField.setText(mTripDateBR);
                    //Toast.makeText(ViagemActivity.this, "DATA = " + dataViagem, Toast.LENGTH_SHORT).show();
                }
            };

    @Override
    public void onClick(View v) {
        if (v == mDateButton)
            showDialog(DATE_DIALOG_ID);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

//    private void setEditingEnabled(boolean enabled) {
//
//        mDepartureField.setEnabled(enabled);
//        mArrivalField.setEnabled(enabled);
//        mDateField.setEnabled(enabled);
//
//
//        if (enabled) {
//            mSubmitButton.setVisibility(View.VISIBLE);
//        } else {
//            mSubmitButton.setVisibility(View.GONE);
//        }
//    }

    public void GoToMainActivity() {

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    // [START write_fan_out]
    private void writeNewTrip(String userId, String username, String departure, String arrival, String date) {
        // Create new trip at /user-trips/$userid/$tripid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("trips").push().getKey();
        Trip trip = new Trip(userId, username, departure, arrival, date);
        Map<String, Object> tripValues = trip.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/trips/" + key, tripValues);
        childUpdates.put("/user-trips/" + userId + "/" + key, tripValues);

        mDatabase.updateChildren(childUpdates);
    }
    // [END write_fan_out]
}
