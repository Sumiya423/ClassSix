package com.example.sixthclass;

public class Model {
    private int position;
    private String name;
    private String top;

    public Model(int position, String name, String top) {
        this.position = position;
        this.name = name;
        this.top = top;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }
}
