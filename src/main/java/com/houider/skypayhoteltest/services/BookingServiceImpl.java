package com.houider.skypayhoteltest.services;

import java.util.Date;

import com.houider.skypayhoteltest.models.Room;
import com.houider.skypayhoteltest.models.User;

public interface BookingServiceImpl {
    void bookRoom(User User, Room room, Date checkIn, Date checkOut);

    void printBookings();
}
