package com.example.realmadridmanagersystem290322.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PlayerDto {

    @JsonIgnore
    private Long playerId;

    @JsonProperty("Full name")
    private String name;

    @JsonProperty("Birthday")
    private String dateOfBirth;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("Height")
    private Double height;

    @JsonProperty("Position")
    private String position;

    @JsonProperty("Join date")
    private String joinedDate;

}
