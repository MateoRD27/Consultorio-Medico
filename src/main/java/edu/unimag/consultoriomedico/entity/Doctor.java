package edu.unimag.consultoriomedico.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "identification_number", nullable = false, unique = true)
    private Long identificationNumber;

    @Column(name = "specialty", nullable = false)
    private String specialty;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "available_from", nullable = false) //Horario de inicio
    private String availableFrom;

    @Column(name = "available_to", nullable = false) //Horario de fin
    private String availableTo;
}
