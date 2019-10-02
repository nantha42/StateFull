package com.example.statefull;

public class Thought {
    int i;
    String text;
    String time;
    int thoughtColor;

    Thought(int i, String text) {
        this.i = i;
        this.text = text;
    }

    Thought() {
    }

    String getTime() {
        return time;
    }

    void setTime(String s) {
        time = s;
    }

    String getText() {
        return text;
    }

    void setText(String s) {
        text = s;
    }
}
