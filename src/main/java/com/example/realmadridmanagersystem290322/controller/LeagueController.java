package com.example.realmadridmanagersystem290322.controller;

import com.example.realmadridmanagersystem290322.dto.LeagueDto;
import com.example.realmadridmanagersystem290322.service.LeagueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/league")
public class LeagueController {

    private final LeagueService leagueService;

    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    // Save a new league into a exist club
    @PostMapping(path = "/saveNewLeague/{seasonOfClub}")
    public ResponseEntity<LeagueDto> saveNewLeagueByClubSeason(@RequestBody LeagueDto leagueDto,
                                                               @PathVariable(value = "seasonOfClub") String seasonOfClub) {
        LeagueDto newLeagueDto = this.leagueService.saveNewLeagueByClubSeason(leagueDto, seasonOfClub);
        return new ResponseEntity<>(newLeagueDto, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/deleteLeague/{clubId}/{leagueId}")
    public ResponseEntity<String> deleteLeagueById(@PathVariable(value = "clubId") Long clubId,
                                 @PathVariable(value = "leagueId") Long leagueId) {
        if(this.leagueService.checkIdForDelete(leagueId, clubId)) {
            this.leagueService.deleteLeagueById(clubId, leagueId);
            return new ResponseEntity<>("Delete successfully !!!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Delete fail !!!", HttpStatus.OK);
    }
}
