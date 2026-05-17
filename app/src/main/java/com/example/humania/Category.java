package com.example.humania;

public class Category {
    private String name;
    private String icon; // Emoji or icon resource name

    public Category() {}

    public Category(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDisplayName() {
        return (icon != null ? icon + " " : "") + name;
    }
}
