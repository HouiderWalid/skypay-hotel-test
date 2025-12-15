package com.houider.skypayhoteltest;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.houider.skypayhoteltest.models.RoomType;
import com.houider.skypayhoteltest.services.BookingService;
import com.houider.skypayhoteltest.services.Service;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Service service = new Service(new BookingService());

        service.setRoom(1, RoomType.STANDARD, 1000);
        service.setRoom(2, RoomType.JUNIOR, 2000);
        service.setRoom(3, RoomType.SUITE, 3000);

        service.setUser(1, 5000);
        service.setUser(2, 10000);

        try {
            service.bookRoom(1, 2,
                    Date.from(LocalDate.of(2026, 6, 30).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    Date.from(LocalDate.of(2026, 7, 7).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            service.bookRoom(1, 2, Date.from(LocalDate.of(2026, 7, 7).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    Date.from(LocalDate.of(2026, 6, 30).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        service.bookRoom(1, 1, Date.from(LocalDate.of(2026, 7, 7).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDate.of(2026, 7, 8).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        try {
            service.bookRoom(2, 1, Date.from(LocalDate.of(2026, 7, 7).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    Date.from(LocalDate.of(2026, 7, 9).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        service.bookRoom(2, 3, Date.from(LocalDate.of(2026, 7, 7).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDate.of(2026, 7, 8).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        try {
            service.setRoom(1, RoomType.SUITE, 10000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        service.printAll();
        service.printAllUsers();
    }
}
