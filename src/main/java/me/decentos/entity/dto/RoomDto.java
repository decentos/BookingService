package me.decentos.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.decentos.entity.model.Room;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private int roomNumber;
    private int roomFloor;
    private int bedsCount;
    private int isDelete;

    public static RoomDto convertToDto(Room entity) {
        return RoomDto.builder()
                .roomNumber(entity.getRoomNumber())
                .roomFloor(entity.getRoomFloor())
                .bedsCount(entity.getBedsCount())
                .isDelete(entity.getIsDelete())
                .build();
    }

    public static Room convertToEntity(RoomDto dto) {
        return Room.builder()
                .roomNumber(dto.getRoomNumber())
                .roomFloor(dto.getRoomFloor())
                .bedsCount(dto.getBedsCount())
                .isDelete(dto.getIsDelete())
                .build();
    }
}
