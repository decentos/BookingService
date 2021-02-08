package me.decentos.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.decentos.entity.dto.BookingDto;
import me.decentos.entity.dto.RoomDto;
import me.decentos.service.BookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bookingservice")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/bookings")
    @ApiOperation(value = "Method for getting all bookings")
    public ResponseEntity<List<BookingDto>> getAllBookings() {
        return ResponseEntity.ok(bookingService.findAllBookings());
    }

    @GetMapping("/bookings/{id}")
    @ApiOperation(value = "Method for getting booking by id")
    public ResponseEntity<BookingDto> getBookingById(
            @ApiParam(name = "id", value = "Booking ID", required = true) @PathVariable("id") int id
    ) {
        return ResponseEntity.ok(bookingService.findBookingById(id));
    }

    @GetMapping("/bookings/period")
    @ApiOperation(value = "Method for getting all bookings for period")
    public ResponseEntity<List<BookingDto>> getAllBookingsByPeriod(
            @ApiParam(name = "arrivalDate", value = "Start period: yyyy-MM-dd", required = true) @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate arrivalDate,
            @ApiParam(name = "departureDate", value = "End period: yyyy-MM-dd", required = true) @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate departureDate
    ) {
        return ResponseEntity.ok(bookingService.findAllBookingsByPeriod(arrivalDate, departureDate));
    }

    @GetMapping("/bookings/room/{id}")
    @ApiOperation(value = "Method for getting all bookings for room")
    public ResponseEntity<List<BookingDto>> findAllBookingsByRoomId(
            @ApiParam(name = "id", value = "Room ID", required = true) @PathVariable("id") int roomId
    ) {
        return ResponseEntity.ok(bookingService.findAllBookingsByRoomId(roomId));
    }

    @GetMapping("/bookings/rooms")
    @ApiOperation(value = "Method for getting all not booking rooms for period")
    public ResponseEntity<List<RoomDto>> getAllNotBookingRoomsForPeriod(
            @ApiParam(name = "arrivalDate", value = "Start period: yyyy-MM-dd", required = true) @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate arrivalDate,
            @ApiParam(name = "departureDate", value = "End period: yyyy-MM-dd", required = true) @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate departureDate
    ) {
        return ResponseEntity.ok(bookingService.findAllNotBookingRoomsForPeriod(arrivalDate, departureDate));
    }

    @PostMapping("/bookings")
    @ApiOperation(value = "Method for creating a new booking")
    public ResponseEntity<BookingDto> bookingRoom(
            @ApiParam(name = "arrivalDate", value = "Arrival date: yyyy-MM-dd", required = true) @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate arrivalDate,
            @ApiParam(name = "departureDate", value = "Departure date: yyyy-MM-dd", required = true) @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate departureDate,
            @ApiParam(name = "roomId", value = "Room ID", required = true) @RequestParam int roomId
    ) {
        return ResponseEntity.ok(bookingService.bookingRoom(arrivalDate, departureDate, roomId));
    }

    @PutMapping("/bookings/{id}")
    @ApiOperation(value = "Method for updating booking by id")
    public ResponseEntity<BookingDto> updateBooking(
            @ApiParam(name = "id", value = "Booking ID", required = true) @PathVariable("id") int id,
            @ApiParam(name = "arrivalDate", value = "Arrival date: yyyy-MM-dd", required = true) @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate arrivalDate,
            @ApiParam(name = "departureDate", value = "Departure date: yyyy-MM-dd", required = true) @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate departureDate,
            @ApiParam(name = "roomId", value = "Room ID", required = true) @RequestParam int roomId
    ) {
        return ResponseEntity.ok(bookingService.updateBooking(id, arrivalDate, departureDate, roomId));
    }

    @DeleteMapping("/bookings/{id}")
    @ApiOperation(value = "Method for deleting booking by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookingById(
            @ApiParam(name = "id", value = "Booking ID", required = true) @PathVariable("id") int id
    ) {
        bookingService.deleteBookingById(id);
    }

    @DeleteMapping("/bookings/room/{id}")
    @ApiOperation(value = "Method for deleting all bookings for room")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllBookingsByRoomId(
            @ApiParam(name = "id", value = "Room ID", required = true) @PathVariable("id") int roomId
    ) {
        bookingService.deleteAllBookingsByRoomId(roomId);
    }
}
