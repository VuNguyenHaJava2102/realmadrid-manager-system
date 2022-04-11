package com.example.realmadridmanagersystem290322.service;

import com.example.realmadridmanagersystem290322.dto.ClubDto;

import java.util.List;

public interface ClubService {

    ClubDto saveNewClub(ClubDto clubDto);

    List<ClubDto> getAllClubDtos();

    ClubDto findClubBySeason(String season);

    boolean checkClubExistsBySeason(String season);
}
