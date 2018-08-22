package br.com.rnsolucoesweb.viagem.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MinhasViagensFragment extends ViagemListFragment {

    public MinhasViagensFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // Todas as Minhas viagens agendadas
        return databaseReference.child("user-viagens")
                .child(getUid());
    }
}
