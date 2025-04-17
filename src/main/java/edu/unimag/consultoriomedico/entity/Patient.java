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
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    @NotBlank(message = "Full name is mandatory")
    private String fullName;

    @Column(name = "identification_number", nullable = false, unique = true)
    @NotNull(message = "Identification number is mandatory")
    private Long identificationNumber;

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Column(name = "phone_number", nullable = false, unique = true)
    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;

    //un paciente puede tener muchas citas
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();

}
