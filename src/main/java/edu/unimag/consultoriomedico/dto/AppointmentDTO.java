package edu.unimag.consultoriomedico.dto;

import edu.unimag.consultoriomedico.entity.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
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
public class AppointmentDTO {
    private Long id;

    @NotNull( message = "Patient is mandatory")
    private Long patientId;

    @NotNull( message = "Doctor is mandatory")
    private Long doctorId;

    @NotNull( message = "Consult room is mandatory")
    private Long consultRoomId;

    @Future( message = "Start time must be in the future")
    private LocalDateTime startTime;

    @Future( message = "End time must be in the future")
    private LocalDateTime endTime;

    private Status status;

}
