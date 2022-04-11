package com.example.realmadridmanagersystem290322.service.serviceImpl;

import com.example.realmadridmanagersystem290322.dto.LeagueDto;
import com.example.realmadridmanagersystem290322.entity.Club;
import com.example.realmadridmanagersystem290322.entity.League;
import com.example.realmadridmanagersystem290322.repository.ClubRepository;
import com.example.realmadridmanagersystem290322.repository.LeagueRepository;
import com.example.realmadridmanagersystem290322.service.LeagueService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class LeagueServiceImpl implements LeagueService {

    private final ClubRepository clubRepository;
    private final LeagueRepository leagueRepository;
    private final ModelMapper modelMapper;

    public LeagueServiceImpl(ClubRepository clubRepository,
                             LeagueRepository leagueRepository,
                             ModelMapper modelMapper) {
        this.clubRepository = clubRepository;
        this.leagueRepository = leagueRepository;
        this.modelMapper = modelMapper;
    }

    // League entity -> dto using modelmapper
    private LeagueDto leagueEntityToDto(League league) {
        return this.modelMapper.map(league, LeagueDto.class);
    }

    // League dto -> entity using modelmapper
    private League leagueDtoToEntity(LeagueDto leagueDto) {
        return this.modelMapper.map(leagueDto, League.class);
    }

    // Save new league
    @Override
    public LeagueDto saveNewLeagueByClubSeason(LeagueDto leagueDto, String seasonOfClub) {
        League league = leagueDtoToEntity(leagueDto);

        Club findClubById = this.clubRepository.findBySeason(seasonOfClub);

        league.setClub(findClubById);
        League newLeague = this.leagueRepository.save(league);
        return leagueEntityToDto(newLeague);
    }

    @Override
    public void deleteLeagueById(Long leagueId, Long clubId) {

        Club findClubById = this.clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalStateException("Club doesn't exist!"));

        League findLeagueById = this.leagueRepository.findById(leagueId)
                .orElseThrow(() -> new IllegalStateException("League doesn't exist!"));

        if(!findLeagueById.getClub().getClubId().equals(clubId)) {
            throw new IllegalStateException("This league doesn't belong to this club!");
        }
        this.leagueRepository.deleteById(leagueId);
    }

    @Override
    public boolean checkIdForDelete(Long leagueId, Long clubId) {

        Club findClubById = this.clubRepository.findById(clubId)
                .orElse(null);

        League findLeagueById = this.leagueRepository.findById(leagueId)
                .orElse(null);

        boolean value = false;
        if(findClubById != null && findLeagueById != null) {
            value = findLeagueById.getClub()
                    .getClubId()
                    .equals(findClubById.getClubId());
        }

        if(findClubById == null || findLeagueById == null || value == false) {
            return false;
        }
        return true;
    }
}
