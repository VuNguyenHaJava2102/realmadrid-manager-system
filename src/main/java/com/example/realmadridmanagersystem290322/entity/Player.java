package com.example.realmadridmanagersystem290322.entity;

import lombok.*;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "players_table")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Long playerId;

    @Column(name = "full_name")
    private String name;

    @Column(name = "birthday")
    private Date dateOfBirth;

    @Column(name = "country")
    private String country;

    @Column(name = "height")
    private Double height;

    @Column(name = "position")
    private String position;

    @Column(name = "joined_date")
    private Date joinedDate;

    @ManyToMany(mappedBy = "players")
    private Set<Club> clubs;

}
