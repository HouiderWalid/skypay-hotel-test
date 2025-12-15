package com.houider.skypayhoteltest.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Booking {

    private User user;
    private Room room;
    private Date checkInDate;
    private Date checkOutDate;
    private Date createdAt;

    public Booking(User user, Room room, Date checkInDate, Date checkOutDate) {
        this.user = user;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.createdAt = new Date();
    }

    public User getUser() {
        return user;
    }

    public Room getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void printAllDetails() {
        System.out.println("- User Details:");
        System.out.println("  User ID: " + user.getId());
        System.out.println("  User Bakance: " + user.getBalance());
        System.out.println("- Room Details:");
        System.out.println("  Room Number: " + room.getNumber());
        System.out.println("  Room Type: " + room.getType());
        System.out.println("  Room Price per Night: " + room.getPricePerNight());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Created At: " + formatter.format(this.getCreatedAt()));
        System.out.println("Check-In Date: " + formatter.format(this.getCheckInDate()));
        System.out.println("Check-Out Date: " + formatter.format(this.getCheckOutDate()));
    }
}
