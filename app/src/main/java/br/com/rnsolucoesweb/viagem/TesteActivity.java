package br.com.rnsolucoesweb.viagem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class TesteActivity extends AppCompatActivity {

    private Button btDeparture;
    private Button btArrival;
    private TextView tvDeparture;
    private TextView tvArrival;
    Context context = this;

    @Override  protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

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

    }
}
