package edu.unimag.consultoriomedico.repository;

import edu.unimag.consultoriomedico.entity.ConsultRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultRoomRepository extends JpaRepository<ConsultRoom, Long> {
}
