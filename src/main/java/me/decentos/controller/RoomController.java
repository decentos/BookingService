package me.decentos.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.decentos.entity.dto.RoomDto;
import me.decentos.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/bookingservice")
@RequiredArgsConstructor
@Slf4j
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/rooms")
    @ApiOperation(value = "Method for getting all rooms")
    public ResponseEntity<List<RoomDto>> getAllRooms() {
        return ResponseEntity.ok(roomService.findAllRooms());
    }

    @GetMapping("/rooms/{id}")
    @ApiOperation(value = "Method for getting room by id")
    public ResponseEntity<RoomDto> getRoomById(
            @ApiParam(name = "id", value = "Room ID", required = true) @PathVariable("id") int id
    ) {
        return ResponseEntity.ok(roomService.findRoomById(id));
    }

    @PostMapping("/rooms")
    @ApiOperation(value = "Method for creating a new room")
    public ResponseEntity<RoomDto> createRoom(
            @ApiParam(name = "roomDto", value = "New room parameters", required = true) @RequestBody @Valid RoomDto roomDto
    ) {
        return ResponseEntity.ok(roomService.createRoom(roomDto));
    }

    @PutMapping("/rooms/{id}")
    @ApiOperation(value = "Method for updating room by id")
    public ResponseEntity<RoomDto> updateRoom(
            @ApiParam(name = "id", value = "Room ID", required = true) @PathVariable("id") int id,
            @ApiParam(name = "roomDto", value = "New room parameters", required = true) @RequestBody @Valid RoomDto roomDto
    ) {
        return ResponseEntity.ok(roomService.updateRoom(id, roomDto));
    }

    @DeleteMapping("/rooms/{id}")
    @ApiOperation(value = "Method for deleting room by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoomById(
            @ApiParam(name = "id", value = "Room ID", required = true) @PathVariable("id") int id
    ) {
        roomService.deleteRoomById(id);
    }
}
