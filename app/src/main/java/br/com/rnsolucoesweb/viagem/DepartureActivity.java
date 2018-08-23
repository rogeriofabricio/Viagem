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

public class DepartureActivity extends AppCompatActivity {

    public String mDeparture;
    public ListView listViewDeparture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departure);

        // Array of strings...
        final String[] departureArray = {"Recife", "Gravatá", "Bezerros", "Caruaru", "Belo Jardim", "Pesqueira", "Arcoverde", "Algodões", "Custódia", "Sítio dos Nunes", "Serra Talhada"};

        ArrayAdapter departureAdapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview_departure, departureArray);

        listViewDeparture = findViewById(R.id.departureListvire);
        listViewDeparture.setAdapter(departureAdapter);

        //Set Listview title
        TextView departureTextView = new TextView(this);
        departureTextView.setTextSize(20);
        departureTextView.setTypeface(null, Typeface.BOLD);
        departureTextView.setTextColor(Color.parseColor("#FFFFFF"));
        departureTextView.setBackgroundColor(Color.parseColor("#FFA3A7A7"));
        departureTextView.setPadding(25, 25, 15, 25);
        departureTextView.setText("CIDADE PARTIDA:");
        listViewDeparture.addHeaderView(departureTextView);

        listViewDeparture.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                mDeparture = departureArray[position - 1].toString();
                //Toast.makeText(getApplicationContext(), "Clicou no item " + messageOrigem, Toast.LENGTH_LONG).show();

                //Next Activity
                GoToArrival();
            }
        });
    }

    public void GoToArrival() {

        Intent i = new Intent(this,ArrivalActivity.class);
        Bundle departure = new Bundle();
        departure.putString("departure", mDeparture);
        i.putExtras(departure);
        startActivity(i);
    }
}
