package com.houider.skypayhoteltest.models;

public class Room {

    private int number;
    private int pricePerNight = 0;
    private RoomType type;

    public Room(int number, int pricePerNight, RoomType type) {
        this.number = number;
        this.pricePerNight = pricePerNight;
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public int getPricePerNight() {
        return pricePerNight;
    }

    public RoomType getType() {
        return type;
    }

    public void printAllDetails() {
        System.out.println("Room Number: " + this.getNumber());
        System.out.println("Room Type: " + this.getType());
        System.out.println("Room Price per Night: " + this.getPricePerNight());
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public void setPricePerNight(int pricePerNight) {
        this.pricePerNight = pricePerNight;
    }
}
