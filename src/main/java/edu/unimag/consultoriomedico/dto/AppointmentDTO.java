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

    @NotNull
    private Long patientId;

    @NotNull
    private Long doctorId;

    @NotNull
    private Long consultRoomId;

    @Future
    private LocalDateTime startTime;

    @Future
    private LocalDateTime endTime;

    private Status status;

}
