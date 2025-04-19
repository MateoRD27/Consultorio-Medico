package edu.unimag.consultoriomedico.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalTime;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "consult_room")
public class ConsultRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    @NotNull(message = "Room name is mandatory")
    @NotBlank(message = "Room name is mandatory")
    private String name;

    @Column(name = "room_number", nullable = false, unique = true)
    @NotNull(message = "Room number is mandatory")
    private Integer roomNumber;

    @Column(name = "floor", nullable = false) //Piso
    @NotNull(message = "Floor is mandatory")
    private Integer floor;

    @Column(name = "description")
    @Size(max = 500, message = "Description must be less than 255 characters")
    private String description;

    //una sala de consulta tiene muchas citas
    @OneToMany(mappedBy = "consultRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();


}
