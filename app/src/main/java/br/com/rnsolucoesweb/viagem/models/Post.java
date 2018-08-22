package br.com.rnsolucoesweb.viagem.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties
public class Post {

    public String uid;
    public String author;
//    public String title;
//    public String body;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    //Viagens
    public String origem;
    public String destino;

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String uid, String author, String origem, String destino) {
        this.uid = uid;
        this.author = author;
//        this.title = title;
//        this.body = body;
        //Viagem
        this.origem = origem;
        this.destino = destino;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
//        result.put("title", title);
//        result.put("body", body);
//        result.put("starCount", starCount);
//        result.put("stars", stars);
        //Viagem
        result.put("origem", origem);
        result.put("destino", destino);

        return result;
    }
    // [END post_to_map]

}
// [END post_class]
