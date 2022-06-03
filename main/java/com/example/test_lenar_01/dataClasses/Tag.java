package com.example.test_lenar_01.dataClasses;

public class Tag {
    static int nextId = 0;

    private int id;
    private String name;
    private long popularity;
    private boolean isSelected;

    public Tag(String name) {
        this.name = name;
        id = Tag.nextId++;
        popularity = 0;
        isSelected = false;
    }

    public String getName() {
        return name;
    }

    public long getPopularity() {
        return popularity;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void select(){
        isSelected = true;
    }

    public void deSelect(){
        isSelected = false;
    }
}
