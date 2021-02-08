package me.decentos.entity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "roomsIdSeq", sequenceName = "rooms_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roomsIdSeq")
    private Integer id;

    @Column(name = "room_number")
    private Integer roomNumber;

    @Column(name = "room_floor")
    private Integer roomFloor;

    @Column(name = "beds_count")
    private Integer bedsCount;

    @Column(name = "delete")
    private int isDelete;

    @OneToMany(mappedBy = "room")
    @JsonBackReference
    private Set<Booking> bookings;
}
