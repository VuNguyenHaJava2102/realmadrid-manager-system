package com.example.realmadridmanagersystem290322.service;

import com.example.realmadridmanagersystem290322.dto.LeagueDto;

public interface LeagueService {

    LeagueDto saveNewLeagueByClubSeason(LeagueDto leagueDto, String seasonOfClub);

    void deleteLeagueById(Long leagueId, Long clubId);

    boolean checkIdForDelete(Long leagueId, Long clubId);
}
