package com.example.statefull;

public class Event {
    String desc;
    String impact;
    String mood;
    String day_id;
    int id;

    Event(int id, String d, String i, String m) {
        desc = d;
        impact = i;
        mood = m;

        this.id = id;
    }
}
