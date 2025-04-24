package edu.unimag.consultoriomedico.repository;

import edu.unimag.consultoriomedico.entity.ConsultRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsultRoomRepository extends JpaRepository<ConsultRoom, Long> {
    // buscar un consultorio por su roomNumber
    Optional<ConsultRoom> findByRoomNumber(Integer roomNumber);

}
