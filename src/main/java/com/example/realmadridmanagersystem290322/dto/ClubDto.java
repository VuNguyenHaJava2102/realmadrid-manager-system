package com.example.realmadridmanagersystem290322.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
public class ClubDto {

    @JsonIgnore
    private Long clubId;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("Season")
    @NotEmpty(message = "Season should not be null")
    private String season;

    @JsonProperty("Leagues")
    private Set<LeagueDto> leagues;

    @JsonProperty("Players")
    private Set<PlayerDto> players;

}
