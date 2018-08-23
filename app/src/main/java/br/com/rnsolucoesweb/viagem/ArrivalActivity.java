package br.com.rnsolucoesweb.viagem;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ArrivalActivity extends AppCompatActivity {

    public String mDepartureR;
    public String mArrival;
    ListView listViewArrival;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrival);

        // Get the Intent that started this activity and extract the string
        Bundle departure = getIntent().getExtras();
        mDepartureR = departure.getString("departure");

        // Array of strings...
        final String[] ArrivalArray = {"Recife", "Gravatá", "Bezerros", "Caruaru", "Belo Jardim", "Pesqueira", "Arcoverde", "Algodões", "Custódia", "Sítio dos Nunes", "Serra Talhada"};

        ArrayAdapter ArrivalAdapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview_arrival, ArrivalArray);

        listViewArrival = findViewById(R.id.arrivalListview);
        listViewArrival.setAdapter(ArrivalAdapter);

        //Set Listview title
        TextView arrivalTextView = new TextView(this);
        arrivalTextView.setTextSize(20);
        arrivalTextView.setTypeface(null, Typeface.BOLD);
        arrivalTextView.setTextColor(Color.parseColor("#FFFFFF"));
        arrivalTextView.setBackgroundColor(Color.parseColor("#FFA3A7A7"));
        arrivalTextView.setPadding(25, 25, 15, 25);
        arrivalTextView.setText("CIDADE DESTINO:");
        listViewArrival.addHeaderView(arrivalTextView);

        listViewArrival.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                mArrival = ArrivalArray[position - 1].toString();
                //Toast.makeText(getApplicationContext(), "Clicou no item " + messageDestino, Toast.LENGTH_LONG).show();

                //Next Activity
                GoToNewTripActivity();
            }
        });
    }

    public void GoToNewTripActivity() {
        Intent ii = new Intent(this,NewTripActivity.class);
        Bundle arrival = new Bundle();
        arrival.putString("departure", mDepartureR);
        arrival.putString("arrival", mArrival);
        ii.putExtras(arrival);
        startActivity(ii);
    }
}
