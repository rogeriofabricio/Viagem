package br.com.rnsolucoesweb.viagem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;

import br.com.rnsolucoesweb.viagem.PostDetailActivity;
import br.com.rnsolucoesweb.viagem.R;
import br.com.rnsolucoesweb.viagem.models.Post;
import br.com.rnsolucoesweb.viagem.models.Viagem;
import br.com.rnsolucoesweb.viagem.viewholder.PostViewHolder;
import br.com.rnsolucoesweb.viagem.viewholder.ViagemViewHolder;

public abstract class ViagemListFragment extends Fragment {

    private static final String TAG = "ViagemListFragment";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Viagem, ViagemViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    public ViagemListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_todas_viagens, container, false);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = rootView.findViewById(R.id.viagens_list);
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
        Query viagensQuery = getQuery(mDatabase);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Viagem>()
                .setQuery(viagensQuery, Viagem.class)
                .build();

        mAdapter = new FirebaseRecyclerAdapter<Viagem, ViagemViewHolder>(options) {

            @Override
            public ViagemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new ViagemViewHolder(inflater.inflate(R.layout.item_viagem, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(ViagemViewHolder viewHolder, int position, final Viagem model) {
                final DatabaseReference viagemRef = getRef(position);

                // Set click listener for the whole post view
                final String viagemKey = viagemRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch PostDetailActivity
//                        Intent intent = new Intent(getActivity(), DetalheViagemActivity.class);
//                        intent.putExtra(DetalheViagemActivity.EXTRA_POST_KEY, viagemKey);
//                        startActivity(intent);
                    }
                });

                // Determine if the current user has liked this post and set UI accordingly
                if (model.stars.containsKey(getUid())) {
                    viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_24);
                } else {
                    viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_outline_24);
                }

                // Bind Post to ViewHolder, setting OnClickListener for the star button
                viewHolder.bindToViagem(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {
                        // Need to write to both places the post is stored
                        DatabaseReference globalViagemRef = mDatabase.child("viagens").child(viagemRef.getKey());
                        DatabaseReference userViagemRef = mDatabase.child("user-viagens").child(model.uid).child(viagemRef.getKey());

                        // Run two transactions
                        onStarClicked(globalViagemRef);
                        onStarClicked(userViagemRef);
                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);
    }

    // [START post_stars_transaction]
    private void onStarClicked(DatabaseReference viagemRef) {
        viagemRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Viagem v = mutableData.getValue(Viagem.class);
                if (v == null) {
                    return Transaction.success(mutableData);
                }

                if (v.stars.containsKey(getUid())) {
                    // Unstar the post and remove self from stars
                    v.starCount = v.starCount - 1;
                    v.stars.remove(getUid());
                } else {
                    // Star the post and add self to stars
                    v.starCount = v.starCount + 1;
                    v.stars.put(getUid(), true);
                }

                // Set value and report transaction success
                mutableData.setValue(v);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "viagemTransaction:onComplete:" + databaseError);
            }
        });
    }
    // [END post_stars_transaction]


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