package me.decentos.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.decentos.entity.model.Booking;
import me.decentos.entity.model.Room;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private int isDelete;
    private Room room;

    public static BookingDto convertToDto(Booking entity) {
        return BookingDto.builder()
                .arrivalDate(entity.getArrivalDate())
                .departureDate(entity.getDepartureDate())
                .isDelete(entity.getIsDelete())
                .room(entity.getRoom())
                .build();
    }

    public static Booking convertToEntity(BookingDto dto) {
        return Booking.builder()
                .arrivalDate(dto.getArrivalDate())
                .departureDate(dto.getDepartureDate())
                .isDelete(dto.getIsDelete())
                .room(dto.getRoom())
                .build();
    }
}
