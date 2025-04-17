package edu.unimag.consultoriomedico.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //una cita tiene un paciente
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    @NotNull(message = "Patient is mandatory")
    private Patient patient;

    //una cita tiene un doctor
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    @NotNull(message = "Doctor is mandatory")
    private Doctor doctor;

    //una cita tiene una sala de consulta
    @ManyToOne
    @JoinColumn(name = "consult_room_id", nullable = false)
    @NotNull(message = "Consult room is mandatory")
    private ConsultRoom consultRoom;

    @Column(name = "start_time", nullable = false)
    @NotNull(message = "Start time is mandatory")
    @Future(message = "Start time must be in the future")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @OneToOne(mappedBy = "appointment")
    private MedicalRecord medicalRecord;

}
