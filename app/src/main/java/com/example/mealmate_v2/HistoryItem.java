package com.example.mealmate_v2;

public class HistoryItem {

    private String name, type, description;

    public HistoryItem(String name, String type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public String getDescription() { return description; }
}
