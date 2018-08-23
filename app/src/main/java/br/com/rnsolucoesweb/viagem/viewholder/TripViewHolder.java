package br.com.rnsolucoesweb.viagem.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.rnsolucoesweb.viagem.R;
import br.com.rnsolucoesweb.viagem.models.Trip;

public class TripViewHolder extends RecyclerView.ViewHolder {

    public TextView authorView;
    public TextView departureView;
    public TextView arrivalView;
    public TextView datelView;

    public TripViewHolder(View itemView) {
        super(itemView);

        authorView = itemView.findViewById(R.id.trip_author);
        departureView = itemView.findViewById(R.id.trip_departure);
        arrivalView = itemView.findViewById(R.id.trip_arrival);
        datelView = itemView.findViewById(R.id.trip_date);
    }

    public void bindToTrip(Trip trip, View.OnClickListener starClickListener) {

        authorView.setText(trip.author);
        departureView.setText(trip.departure);
        arrivalView.setText(trip.arrival);
        datelView.setText(trip.date);

        //starView.setOnClickListener(starClickListener);
    }
}
