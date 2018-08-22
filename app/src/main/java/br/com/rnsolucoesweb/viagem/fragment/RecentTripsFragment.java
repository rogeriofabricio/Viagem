package br.com.rnsolucoesweb.viagem.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class RecentTripsFragment extends TripListFragment {

    public RecentTripsFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // [START recent_trips_query]
        // Last 100 trips, these are automatically the 100 most recent
        // due to sorting by push() keys
        Query recentTripsQuery = databaseReference.child("trips")
                .limitToFirst(100);
        // [END recent_trips_query]

        return recentTripsQuery;
    }
}
