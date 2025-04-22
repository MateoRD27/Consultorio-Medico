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

    @NotBlank
    private String fullName;

    @NotNull
    private Long identificationNumber;

    @NotBlank
    @Size(min = 3, max = 50, message = "Specialty must be between 3 and 50 characters")
    private String specialty;

    @Email
    @NotBlank
    private String email;

    @NotNull
    private LocalTime availableFrom;

    @NotNull
    private LocalTime availableTo;
}
