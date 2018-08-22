package br.com.rnsolucoesweb.viagem.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START viagem_class]
@IgnoreExtraProperties
public class Viagem {

    public String uid;
    public String author;
    public String origem;
    public String destino;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public Viagem() {
        // Construtor padrão requerido para chamar os DataSnapshot.getValue(Viagem.class)
    }

    public Viagem(String uid, String author, String origem, String destino) {
        this.uid = uid;
        this.author = author;
        this.origem = origem;
        this.destino = destino;
    }

    // [START viagem_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("origem", origem);
        result.put("destino", destino);
        result.put("starCount", starCount);
        result.put("stars", stars);

        return result;
    }
    // [END viagem_to_map]

}
// [END viagem_class]