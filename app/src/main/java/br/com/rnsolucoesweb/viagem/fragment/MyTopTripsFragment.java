package br.com.rnsolucoesweb.viagem.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MyTopTripsFragment extends TripListFragment {

    public MyTopTripsFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // [START my_top_trips_query]
        // My top trips by number of stars
        String myUserId = getUid();
        Query myTopTripsQuery = databaseReference.child("user-trips").child(myUserId)
                .orderByChild("starCount");
        // [END my_top_trips_query]

        return myTopTripsQuery;
    }
}
