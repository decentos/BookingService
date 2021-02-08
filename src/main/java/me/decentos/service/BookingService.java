package me.decentos.service;

import me.decentos.entity.dto.BookingDto;
import me.decentos.entity.dto.RoomDto;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    List<BookingDto> findAllBookings();

    BookingDto findBookingById(int id);

    List<BookingDto> findAllBookingsByPeriod(LocalDate arrivalDate, LocalDate departureDate);

    List<BookingDto> findAllBookingsByRoomId(int roomId);

    List<RoomDto> findAllNotBookingRoomsForPeriod(LocalDate arrivalDate, LocalDate departureDate);

    BookingDto bookingRoom(LocalDate arrivalDate, LocalDate departureDate, int roomId);

    BookingDto updateBooking(int id, LocalDate arrivalDate, LocalDate departureDate, int roomId);

    void deleteBookingById(int id);

    void deleteAllBookingsByRoomId(int roomId);
}
