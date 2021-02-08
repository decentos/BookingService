package me.decentos.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.decentos.entity.dto.BookingDto;
import me.decentos.entity.dto.RoomDto;
import me.decentos.entity.model.Booking;
import me.decentos.entity.model.Room;
import me.decentos.repository.BookingRepository;
import me.decentos.repository.RoomRepository;
import me.decentos.service.BookingService;
import me.decentos.service.RoomService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final RoomService roomService;

    @Override
    public List<BookingDto> findAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .filter(booking -> booking.getIsDelete() == 0 && booking.getRoom().getIsDelete() == 0)
                .map(BookingDto::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookingDto findBookingById(int id) {
        Booking existingBooking = checkAndGetBooking(id);
        return BookingDto.convertToDto(existingBooking);
    }

    @Override
    public List<BookingDto> findAllBookingsByPeriod(LocalDate arrivalDate, LocalDate departureDate) {
        return bookingRepository.findAll()
                .stream()
                .filter(booking -> booking.getIsDelete() == 0 && booking.getRoom().getIsDelete() == 0)
                .filter(booking -> (booking.getArrivalDate().isAfter(arrivalDate) || booking.getArrivalDate().isEqual(arrivalDate))
                        && (booking.getDepartureDate().isBefore(departureDate) || booking.getDepartureDate().isEqual(departureDate)))
                .map(BookingDto::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> findAllBookingsByRoomId(int roomId) {
        return bookingRepository.findAllByRoomId(roomId)
                .stream()
                .filter(booking -> booking.getIsDelete() == 0 && booking.getRoom().getIsDelete() == 0)
                .map(BookingDto::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<RoomDto> findAllNotBookingRoomsForPeriod(LocalDate arrivalDate, LocalDate departureDate) {
        List<BookingDto> bookingsForPeriod = findAllBookings()
                .stream()
                .map(BookingDto::convertToEntity)
                .filter(booking -> roomIsBooking(booking, arrivalDate, departureDate))
                .map(BookingDto::convertToDto)
                .collect(Collectors.toList());

        Set<RoomDto> bookingRooms = bookingsForPeriod.stream().map(BookingDto::getRoom).map(RoomDto::convertToDto).collect(Collectors.toSet());
        Set<RoomDto> rooms = new HashSet<>(roomService.findAllRooms());

        rooms.removeAll(bookingRooms);
        return new ArrayList<>(rooms);
    }

    @Override
    public BookingDto bookingRoom(LocalDate arrivalDate, LocalDate departureDate, int roomId) {
        checkPeriod(arrivalDate, departureDate, roomId);
        Room room = roomRepository.findById(roomId);
        Booking prepareBooking = Booking.builder()
                .arrivalDate(arrivalDate)
                .departureDate(departureDate)
                .room(room)
                .build();

        Booking createdBooking = bookingRepository.save(prepareBooking);
        log.info("Successfully saved a new booking from {} to {} for room number {}", createdBooking.getArrivalDate(), createdBooking.getDepartureDate(), createdBooking.getRoom().getId());
        return BookingDto.convertToDto(createdBooking);
    }

    @Override
    public BookingDto updateBooking(int id, LocalDate arrivalDate, LocalDate departureDate, int roomId) {
        checkAndGetBooking(id);
        checkPeriod(id, arrivalDate, departureDate, roomId);
        Room room = roomRepository.findById(roomId);
        Booking prepareBooking = Booking.builder()
                .id(id)
                .arrivalDate(arrivalDate)
                .departureDate(departureDate)
                .room(room)
                .build();
        Booking updatedBooking = bookingRepository.save(prepareBooking);
        log.info("Successfully updated a new booking from {} to {} for room number {}", updatedBooking.getArrivalDate(), updatedBooking.getDepartureDate(), updatedBooking.getRoom().getId());
        return BookingDto.convertToDto(updatedBooking);
    }

    @Override
    public void deleteBookingById(int id) {
        Booking existingBooking = checkAndGetBooking(id);
        existingBooking.setIsDelete(1);
        bookingRepository.save(existingBooking);
        log.info("Booking - {} was marked as deleted for room number - {}", existingBooking.getId(), existingBooking.getRoom().getRoomNumber());
    }

    @Override
    public void deleteAllBookingsByRoomId(int roomId) {
        bookingRepository.findAllByRoomId(roomId)
                .forEach(booking -> {
                            booking.setIsDelete(1);
                            bookingRepository.save(booking);
                            log.info("Booking - {} was marked as deleted for room number - {}", booking.getId(), booking.getRoom().getRoomNumber());
                        }
                );
    }

    private Booking checkAndGetBooking(int id) {
        Booking existingBooking = bookingRepository.findById(id);
        if (existingBooking == null) {
            throw new NoSuchElementException(String.format("Booking with id %s not found", id));
        }
        return existingBooking;
    }

    private void checkPeriod(LocalDate arrivalDate, LocalDate departureDate, int roomId) {
        List<Booking> bookings = bookingRepository.findAllByRoomId(roomId)
                .stream()
                .filter(booking -> booking.getIsDelete() == 0 && booking.getRoom().getIsDelete() == 0)
                .collect(Collectors.toList());

        boolean isBlockingPeriod = isBlockingPeriod(bookings, arrivalDate, departureDate);
        if (isBlockingPeriod) {
            throw new IllegalArgumentException("Booking is not possible during the selected period");
        }
    }

    private void checkPeriod(int bookingId, LocalDate arrivalDate, LocalDate departureDate, int roomId) {
        List<Booking> bookings = bookingRepository.findAllByRoomId(roomId)
                .stream()
                .filter(booking -> booking.getIsDelete() == 0 && booking.getRoom().getIsDelete() == 0)
                .filter(booking -> booking.getId() != bookingId)
                .collect(Collectors.toList());

        boolean isBlockingPeriod = isBlockingPeriod(bookings, arrivalDate, departureDate);
        if (isBlockingPeriod) {
            throw new IllegalArgumentException("Booking is not possible during the selected period");
        }
    }

    private boolean isBlockingPeriod(List<Booking> bookings, LocalDate arrivalDate, LocalDate departureDate) {
        return bookings.stream().anyMatch(booking -> roomIsBooking(booking, arrivalDate, departureDate));
    }

    private boolean roomIsBooking(Booking booking, LocalDate arrivalDate, LocalDate departureDate) {
        return !((arrivalDate.isBefore(booking.getArrivalDate()) && departureDate.isBefore(booking.getArrivalDate()))
                || (arrivalDate.isAfter(booking.getDepartureDate()) && departureDate.isAfter(booking.getDepartureDate())));
    }
}
