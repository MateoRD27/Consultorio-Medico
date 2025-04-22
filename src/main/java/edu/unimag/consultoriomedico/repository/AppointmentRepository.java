package edu.unimag.consultoriomedico.repository;

import edu.unimag.consultoriomedico.entity.Appointment;
import edu.unimag.consultoriomedico.entity.ConsultRoom;
import edu.unimag.consultoriomedico.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    //citas en conflicto
    @Query("SELECT a FROM Appointment a WHERE (a.doctor.id = :doctorId OR a.consultRoom.id = :roomId) " +
            "AND a.startTime < :endTime AND a.endTime > :startTime")
    List<Appointment> findConflictsAppointment(@Param("doctorId") Long doctorId,
                                         @Param("roomId") Long roomId,
                                         @Param("startTime") LocalDateTime startTime,
                                         @Param("endTime") LocalDateTime endTime);

    //Buscar si la cita terminó
    @Query("SELECT a FROM Appointment a WHERE a.id = :appointmentId AND a.status = 'COMPLETED'")
    Optional<Appointment> findCompletedAppointment(Long appointmentId);

    //verificar si un doctor ya tiene una cita en un intervalo de tiempo
    boolean existsByDoctorAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(Doctor doctor, LocalDateTime endTime, LocalDateTime startTime);

    //verificar si un consultorio ya tiene una cita en un intervalo de tiempo
    boolean existsByConsultRoomAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(ConsultRoom consultRoom, LocalDateTime endTime, LocalDateTime startTime);


}
