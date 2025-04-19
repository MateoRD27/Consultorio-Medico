package edu.unimag.consultoriomedico.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    @NotBlank(message = "Full name is mandatory")
    private String fullName;

    @Column(name = "identification_number", nullable = false, unique = true)
    @NotNull(message = "Identification number is mandatory")
    private Long identificationNumber;

    @Column(name = "specialty", nullable = false)
    @NotBlank(message = "Specialty is mandatory")
    @Size(min = 3, max = 50, message = "Specialty must be between 3 and 50 characters")
    private String specialty;

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Column(name = "available_from", nullable = false) //Horario de inicio
    @NotNull(message = "Available from is mandatory")
    private LocalTime availableFrom;

    @Column(name = "available_to") //Horario de fin
    @NotNull(message = "Available from is mandatory")
    private LocalTime availableTo;

    //un doctor tiene muchas citas
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();
}
