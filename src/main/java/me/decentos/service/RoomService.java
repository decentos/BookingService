package me.decentos.service;

import me.decentos.entity.dto.RoomDto;

import java.util.List;

public interface RoomService {

    List<RoomDto> findAllRooms();

    RoomDto findRoomById(int id);

    RoomDto createRoom(RoomDto roomDto);

    RoomDto updateRoom(int id, RoomDto roomDto);

    void deleteRoomById(int id);
}
