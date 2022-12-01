package model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@Table(name = "sights")
public class Sight {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    @Column(name = "id_town")
    @ManyToOne(cascade = CascadeType.ALL)
    private Integer idTown;

    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum")
    private SightType type;

    @Setter
    @Getter
    private String information;


}