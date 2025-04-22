package edu.unimag.consultoriomedico.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientDTO {
    private Long id;

    @NotBlank(message = "Full name is mandatory")
    private String fullName;

    @NotNull
    private Long identificationNumber;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;


    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;

}
