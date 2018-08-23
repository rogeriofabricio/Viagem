package br.com.rnsolucoesweb.viagem.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START trip_class]
@IgnoreExtraProperties
public class Trip {

    public String uid;
    public String author;
    public String departure;
    public String arrival;
    public String date;
    public Map<String, Boolean> stars = new HashMap<>();


    public Trip() {
        // Default constructor required for calls to DataSnapshot.getValue(Trip.class)
    }

    public Trip(String uid, String author, String departure, String arrival, String date) {
        this.uid = uid;
        this.author = author;
        this.departure = departure;
        this.arrival = arrival;
        this.date = date;
    }

    // [START trip_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("departure", departure);
        result.put("arrival", arrival);
        result.put("date", date);

        return result;
    }
    // [END trip_to_map]

}
// [END trip_class]
