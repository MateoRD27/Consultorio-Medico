package edu.unimag.consultoriomedico.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

@Table(name="medical_record")
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "appointment_id", referencedColumnName = "id", nullable = false)
    private Appointment appointment;

    //un registro está asosiado a un paciente
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    @NotNull(message = "Patient is mandatory")
    private Patient patient;

    @NotBlank(message = "Diagnosis is mandatory")
    String diagnosis;

    @Lob //puede contener textos largos
    private String notes;

    @CreationTimestamp //asignar automáticamente la fecha y hora en el momento en que se crea la entidad
    @Column(name = "created_at", updatable = false, nullable = false) //la fecha de creación se quede fija
    private LocalDateTime createdAt;

}
