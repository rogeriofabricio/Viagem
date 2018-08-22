package br.com.rnsolucoesweb.viagem.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.rnsolucoesweb.viagem.R;
import br.com.rnsolucoesweb.viagem.models.Trip;

public class TripViewHolder extends RecyclerView.ViewHolder {

    public TextView authorView;
    public TextView origemView;
    public TextView destinoView;

    public TripViewHolder(View itemView) {
        super(itemView);

        authorView = itemView.findViewById(R.id.post_author);
        origemView = itemView.findViewById(R.id.post_origem);
        destinoView = itemView.findViewById(R.id.post_destino);
    }

    public void bindToTrip(Trip trip, View.OnClickListener starClickListener) {

        authorView.setText(trip.author);
        origemView.setText(trip.origem);
        destinoView.setText(trip.destino);

        //starView.setOnClickListener(starClickListener);
    }
}
