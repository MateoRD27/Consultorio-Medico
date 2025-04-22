package edu.unimag.consultoriomedico.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorDTO {
    private Long id;

    @NotBlank( message = "Full name is mandatory")
    private String fullName;

    @NotNull
    private Long identificationNumber;

    @NotBlank(message = "Identification type is mandatory")
    @Size(min = 3, max = 100, message = "Specialty must be between 3 and 100 characters")
    private String specialty;

    @Email( message = "Email should be valid")
    @NotBlank( message = "Email is mandatory")
    private String email;

    @NotNull( message = "Phone number is mandatory")
    private LocalTime availableFrom;

    @NotNull( message = "Phone number is mandatory")
    private LocalTime availableTo;
}
