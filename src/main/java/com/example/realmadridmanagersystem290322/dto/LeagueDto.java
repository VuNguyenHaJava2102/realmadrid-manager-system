package com.example.realmadridmanagersystem290322.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LeagueDto {

    //@JsonProperty("League ID")
    @JsonIgnore
    private Long leagueId;

    @JsonProperty("League Name")
    private String leagueName;

    @JsonProperty("Achievement")
    private String achievement;
}
