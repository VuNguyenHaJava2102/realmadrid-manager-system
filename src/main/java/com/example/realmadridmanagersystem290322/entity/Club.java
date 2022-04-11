package com.example.realmadridmanagersystem290322.entity;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "clubs_table",
        uniqueConstraints = @UniqueConstraint(columnNames = {"club_season"}))
public class Club {

    private final String name = "Real Madrid";

    private final String country = "Spain";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Long clubId;

    @Column(name = "club_season")
    private String season;

    @Column(name = "leagues")
    @OneToMany(mappedBy = "club", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<League> leagues = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "club_player",
            joinColumns = @JoinColumn(name = "club_id", referencedColumnName = "club_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id", referencedColumnName = "player_id"))
    private Set<Player> players = new HashSet<>();

}
