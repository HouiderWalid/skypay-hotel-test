package com.houider.skypayhoteltest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.Test;

import com.houider.skypayhoteltest.exceptions.EntityAlreadyExistException;
import com.houider.skypayhoteltest.exceptions.InsufficientBalanceException;
import com.houider.skypayhoteltest.exceptions.RoomAlreadyBookedException;
import com.houider.skypayhoteltest.models.RoomType;
import com.houider.skypayhoteltest.models.User;
import com.houider.skypayhoteltest.services.BookingService;
import com.houider.skypayhoteltest.services.Service;

public class ServiceTest {

    @Test
    public void userBalanceIsUpdatedCorrectly() {
        Service service = new Service(new BookingService());
        service.setRoom(1, RoomType.STANDARD, 1000);
        User user = service.setUser(1, 5000);
        service.bookRoom(1, 1, Date.from(LocalDate.of(2026, 7, 7).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDate.of(2026, 7, 8).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        assertEquals(user.getBalance(), 4000);
    }

    @Test
    public void checkInMustBeBeforeCheckout() {
        Service service = new Service(new BookingService());
        service.setRoom(1, RoomType.STANDARD, 1000);
        service.setUser(1, 5000);
        assertThrows(IllegalArgumentException.class, () -> {
            service.bookRoom(1, 1, Date.from(LocalDate.of(2026, 7, 7).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    Date.from(LocalDate.of(2026, 7, 4).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        });
    }

    @Test
    public void roomAlreadyExist() {
        Service service = new Service(new BookingService());
        service.setRoom(1, RoomType.STANDARD, 1000);

        assertThrows(EntityAlreadyExistException.class, () -> service.setRoom(2, RoomType.STANDARD, 1000));
        assertThrows(EntityAlreadyExistException.class, () -> service.setRoom(1, RoomType.JUNIOR, 500));
    }

    @Test
    public void userAlreadyExist() {
        Service service = new Service(new BookingService());
        service.setUser(1, 5000);

        assertThrows(EntityAlreadyExistException.class, () -> service.setUser(1, 10000));
    }

    @Test
    public void roomAlreadyBookAtAPeriod() {
        Service service = new Service(new BookingService());
        service.setRoom(1, RoomType.STANDARD, 1000);
        service.setUser(1, 5000);
        service.bookRoom(1, 1, Date.from(LocalDate.of(2026, 7, 7).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDate.of(2026, 7, 8).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        service.setUser(2, 10000);

        assertThrows(RoomAlreadyBookedException.class,
                () -> service.bookRoom(2, 1,
                        Date.from(LocalDate.of(2026, 7, 7).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        Date.from(LocalDate.of(2026, 7, 9).atStartOfDay(ZoneId.systemDefault()).toInstant())));
    }

    @Test
    public void userUnssuficientBalance() {
        Service service = new Service(new BookingService());
        service.setRoom(1, RoomType.STANDARD, 3000);
        service.setUser(1, 4000);

        assertThrows(InsufficientBalanceException.class,
                () -> service.bookRoom(1, 1,
                        Date.from(LocalDate.of(2026, 7, 7).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        Date.from(LocalDate.of(2026, 7, 9).atStartOfDay(ZoneId.systemDefault()).toInstant())));
    }
}
