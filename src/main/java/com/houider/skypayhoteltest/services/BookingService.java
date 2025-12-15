package com.houider.skypayhoteltest.services;

import java.util.ArrayList;
import java.util.Date;

import com.houider.skypayhoteltest.exceptions.InsufficientBalanceException;
import com.houider.skypayhoteltest.exceptions.RoomAlreadyBookedException;
import com.houider.skypayhoteltest.models.Booking;
import com.houider.skypayhoteltest.models.Room;
import com.houider.skypayhoteltest.models.User;

public class BookingService implements BookingServiceImpl {

    ArrayList<Booking> bookings;

    public BookingService() {
        this.bookings = new ArrayList<Booking>();
    }

    @Override
    public void bookRoom(User user, Room room, Date checkIn, Date checkOut) {

        if (this.isBooked(room, checkIn, checkOut)) {
            throw new RoomAlreadyBookedException("Room is already booked for the given period.");
        }

        if (!this.hasSufficientBalance(user, room, checkIn, checkOut)) {
            throw new InsufficientBalanceException("User has insufficient balance for this booking.");
        }

        int bookingPrice = calculateBookingPrice(room, checkIn, checkOut);

        this.bookings.add(new Booking(user, room, checkIn, checkOut));
        user.deductBalance(bookingPrice);
    }

    public boolean hasSufficientBalance(User user, Room room, Date checkIn, Date checkOut) {
        int bookingPrice = calculateBookingPrice(room, checkIn, checkOut);
        return user.getBalance() >= bookingPrice;
    }

    private int calculateBookingPrice(Room room, Date checkIn, Date checkOut) {
        if (!checkOut.after(checkIn)) {
            throw new IllegalArgumentException("Check-out must be after check-in");
        }

        long millisPerDay = 1000L * 60 * 60 * 24;
        int nights = (int) ((checkOut.getTime() - checkIn.getTime()) / millisPerDay);

        return nights * room.getPricePerNight();
    }

    public boolean isBooked(Room room, Date checkIn, Date checkOut) {

        return this.bookings.stream()
                .filter(booking -> booking.getRoom().getNumber() == room.getNumber()
                        && ((checkIn.after(booking.getCheckInDate())
                                && checkIn.before(booking.getCheckOutDate()))
                                || (checkOut.after(booking.getCheckInDate())
                                        && checkOut.before(booking
                                                .getCheckOutDate()))
                                || (checkIn.equals(booking.getCheckInDate())
                                        || checkOut.equals(booking
                                                .getCheckOutDate()))))
                .findFirst()
                .isPresent();
    }

    public void printBookings() {
        System.out.println("Bookings:");
        for (int i = bookings.size() - 1; i >= 0; i--) {
            bookings.get(i).printAllDetails();
            System.out.println("-----------------------");
        }
    }
}
