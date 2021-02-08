package me.decentos.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.decentos.entity.dto.RoomDto;
import me.decentos.entity.model.Room;
import me.decentos.repository.RoomRepository;
import me.decentos.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public List<RoomDto> findAllRooms() {
        return roomRepository.findAll()
                .stream()
                .filter(room -> room.getIsDelete() == 0)
                .map(RoomDto::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto findRoomById(int id) {
        Room existingRoom = checkAndGetRoom(id);
        return RoomDto.convertToDto(existingRoom);
    }

    @Override
    public RoomDto createRoom(RoomDto roomDto) {
        Room prepareRoom = RoomDto.convertToEntity(roomDto);
        Room createdRoom = roomRepository.save(prepareRoom);
        log.info("Successfully saved a new room: {}", createdRoom.getRoomNumber());
        return RoomDto.convertToDto(createdRoom);
    }

    @Override
    public RoomDto updateRoom(int id, RoomDto roomDto) {
        checkAndGetRoom(id);
        Room prepareRoom = RoomDto.convertToEntity(roomDto);
        prepareRoom.setId(id);
        Room updatedRoom = roomRepository.save(prepareRoom);
        log.info("Successfully updated room: {}", updatedRoom.getRoomNumber());
        return RoomDto.convertToDto(updatedRoom);
    }

    @Override
    public void deleteRoomById(int id) {
        Room existingRoom = checkAndGetRoom(id);
        existingRoom.setIsDelete(1);
        roomRepository.save(existingRoom);
        log.info("Room - {}, id - {} was marked as deleted", existingRoom.getRoomNumber(), existingRoom.getId());
    }

    private Room checkAndGetRoom(int id) {
        Room existingRoom = roomRepository.findById(id);
        if (existingRoom == null) {
            throw new NoSuchElementException(String.format("Room with id %s not found", id));
        }
        return existingRoom;
    }
}
