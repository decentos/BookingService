package me.decentos.repository;

import me.decentos.entity.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Booking findById(int id);

    List<Booking> findAllByRoomId(int roomId);

}
