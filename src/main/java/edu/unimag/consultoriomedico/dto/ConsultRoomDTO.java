package edu.unimag.consultoriomedico.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsultRoomDTO {
    private Long id;

    @NotBlank(message = "Room name is mandatory")
    private String name;

    @Positive
    @NotNull(message = "Room number is mandatory")
    private Integer roomNumber;

    @Positive
    @NotNull(message = "Floor is mandatory")
    private Integer floor;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;


}
