package br.com.rnsolucoesweb.viagem.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import br.com.rnsolucoesweb.viagem.R;
import br.com.rnsolucoesweb.viagem.models.Trip;
import br.com.rnsolucoesweb.viagem.viewholder.TripViewHolder;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public abstract class TripListFragment extends Fragment {

    private static final String TAG = "TripListFragment";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Trip, TripViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    public TripListFragment() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_all_trips, container, false);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = rootView.findViewById(R.id.messages_list);
        mRecycler.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query tripsQuery = getQuery(mDatabase);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Trip>()
                .setQuery(tripsQuery, Trip.class)
                .build();

        mAdapter = new FirebaseRecyclerAdapter<Trip, TripViewHolder>(options) {

            @Override
            public TripViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new TripViewHolder(inflater.inflate(R.layout.item_trip, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(TripViewHolder viewHolder, final int position, final Trip model) {
                final DatabaseReference tripRef = getRef(position);

                // Set click listener for the whole trip view
                final String tripKey = tripRef.getKey();
                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        Toast.makeText(getApplicationContext(), "Apagando...", Toast.LENGTH_LONG).show();
                        mDatabase.child("trips").child(tripKey).removeValue();
                        mDatabase.child("user-trips").child(model.uid).child(tripKey).removeValue();

                        return false;
                    }
                });

                // Determine if the current user has liked this trip and set UI accordingly
//                if (model.stars.containsKey(getUid())) {
//                    viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_24);
//                } else {
//                    viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_outline_24);
//                }

                // Bind Trip to ViewHolder, setting OnClickListener for the star button
                viewHolder.bindToTrip(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {
                        // Need to write to both places the trip is stored
                        DatabaseReference globalTripRef = mDatabase.child("trips").child(tripRef.getKey());
                        DatabaseReference userTripRef = mDatabase.child("user-trips").child(model.uid).child(tripRef.getKey());

                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }


    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public abstract Query getQuery(DatabaseReference databaseReference);

}
