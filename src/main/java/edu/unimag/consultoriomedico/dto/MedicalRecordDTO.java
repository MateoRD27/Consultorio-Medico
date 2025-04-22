package edu.unimag.consultoriomedico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalRecordDTO {
    private Long id;

    @NotNull(message = "Appointment ID is mandatory")
    private Long appointmentId;

    @NotNull(message = "Patient ID is mandatory")
    private Long patientId;

    @NotBlank(message = "Diagnosis is mandatory")
    private String diagnosis;

    private String notes;

    private LocalDateTime createdAt;
}
