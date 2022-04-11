package com.example.realmadridmanagersystem290322.controller;

import com.example.realmadridmanagersystem290322.dto.ClubDto;
import com.example.realmadridmanagersystem290322.service.serviceImpl.ClubServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/club")
public class ClubController {

    private final ClubServiceImpl clubService;

    public ClubController(ClubServiceImpl clubService) {
        this.clubService = clubService;
    }

    // Save new club
    @PostMapping(path = "/saveNewClub")
    public ResponseEntity<ClubDto> saveNewClub(@Valid @RequestBody ClubDto clubDto) {
        return new ResponseEntity<>(this.clubService.saveNewClub(clubDto), HttpStatus.CREATED);
    }

//    // Get all clubs
//    @GetMapping(path = "/getAllClubs")
//    public List<ClubDto> getAllClubs() {
//        return this.clubService.getAllClubDtos();
//    }

    // Get all clubs
    @GetMapping(path = "/getAllClubs")
    public ResponseEntity<List<ClubDto>> getAllClubs() {
        return new ResponseEntity<>(this.clubService.getAllClubDtos(), HttpStatus.OK);
    }

    // Get a club
    @GetMapping(path = "/findBySeason/{season}")
    public ResponseEntity<ClubDto> findBySeason(@PathVariable(value = "season") String season) {
        return new ResponseEntity<>(this.clubService.findClubBySeason(season), HttpStatus.OK);
    }

    // Delete a club by season
    @DeleteMapping(path = "/deleteBySeason/{season}")
    public ResponseEntity<String> deleteClubBySeason(@PathVariable(value = "season") String season) {
        if(clubService.checkClubExistsBySeason(season)) {
            this.clubService.deleteCLubBySeason(season);
            return new ResponseEntity<>("Delete club by season successfully !!!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Delete club by season fail !!!", HttpStatus.OK);
    }
}
