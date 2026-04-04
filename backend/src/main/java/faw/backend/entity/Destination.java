package faw.backend.entity;


import faw.backend.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "destinations")
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "dest_seq")
    private Long id;
    private String name;        // country name
    private String capital;
    private String region;
    private Long population;
    private String currency;
    private String flagUrl;
    private String countryCode;
    @Enumerated(EnumType.STRING)
    private Status status = Status.APPROVED;
}
