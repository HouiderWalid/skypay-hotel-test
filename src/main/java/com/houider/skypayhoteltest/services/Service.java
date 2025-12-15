package com.houider.skypayhoteltest.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import com.houider.skypayhoteltest.exceptions.EntityAlreadyExistException;
import com.houider.skypayhoteltest.exceptions.EntityNotFoundException;
import com.houider.skypayhoteltest.models.Room;
import com.houider.skypayhoteltest.models.RoomType;
import com.houider.skypayhoteltest.models.User;

public class Service {

        private ArrayList<Room> rooms;
        private ArrayList<User> users;
        private BookingServiceImpl bookingService;

        public Service(BookingServiceImpl bookingService) {
                this.rooms = new ArrayList<Room>();
                this.users = new ArrayList<User>();
                this.bookingService = bookingService;
        }

        public void setRoom(int roomNumber, RoomType roomType, int roomPricePerNight) {
                Optional<Room> existingRoom = this.rooms.stream()
                                .filter(room -> room.getNumber() == roomNumber
                                                || (room.getType() == roomType
                                                                && room.getPricePerNight() == roomPricePerNight))
                                .findFirst();

                if (existingRoom.isPresent()) {
                        throw new EntityAlreadyExistException("Room already exists");
                }

                Room newRoom = new Room(roomNumber, roomPricePerNight, roomType);
                this.rooms.add(newRoom);
        };

        public void bookRoom(int userId, int roomNumber, Date checkIn, Date checkOut) {

                User bookingUser = this.users.stream()
                                .filter(user -> user.getId() == userId)
                                .findFirst()
                                .orElseThrow(() -> new EntityNotFoundException("User not found"));

                Room bookingRoom = this.rooms.stream()
                                .filter(room -> room.getNumber() == roomNumber)
                                .findFirst()
                                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

                this.bookingService.bookRoom(bookingUser, bookingRoom, checkIn, checkOut);
        };

        public void printAll() {
                System.out.println("Rooms:");
                for (int i = rooms.size() - 1; i >= 0; i--) {
                        rooms.get(i).printAllDetails();
                        System.out.println("-----------------------");
                }
                this.bookingService.printBookings();
        };

        public User setUser(int userId, int balance) {
                Optional<User> existingUser = this.users.stream()
                                .filter(user -> user.getId() == userId)
                                .findFirst();

                if (existingUser.isPresent()) {
                        throw new EntityAlreadyExistException("User already exists");
                }

                User newUser = new User(userId, balance);
                this.users.add(newUser);
                return newUser;
        };

        public void printAllUsers() {
                System.out.println("Users:");
                for (int i = users.size() - 1; i >= 0; i--) {
                        users.get(i).printAllDetails();
                        System.out.println("-----------------------");
                }
        };
}
