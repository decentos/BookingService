package me.decentos.repository;

import me.decentos.entity.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    Room findById(int id);
}
