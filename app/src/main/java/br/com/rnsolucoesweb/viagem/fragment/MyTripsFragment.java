package br.com.rnsolucoesweb.viagem.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MyTripsFragment extends TripListFragment {

    public MyTripsFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // All my trips
        return databaseReference.child("user-trips")
                .child(getUid());
    }
}
