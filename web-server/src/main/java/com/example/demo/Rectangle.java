package com.example.demo;

public class Rectangle {
    //ZAD2.1: Utwórz klasę Rectangle składającą się z całkowitych parametrów: położenia x, położenia y, szerokości,
    // wysokości oraz koloru wyrażonego napisem. Wygeneruj konstruktor, akcesory oraz mutatory do wszystkich pól.
    private int x;
    private int y;
    private int height;
    private int width;
    private String color;

    public Rectangle(int x, int y, int width, int height, String color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getColor() {
        return color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
