package com.example.realmadridmanagersystem290322.service.serviceImpl;

import com.example.realmadridmanagersystem290322.dto.ClubDto;
import com.example.realmadridmanagersystem290322.dto.LeagueDto;
import com.example.realmadridmanagersystem290322.dto.PlayerDto;
import com.example.realmadridmanagersystem290322.entity.Club;
import com.example.realmadridmanagersystem290322.entity.League;
import com.example.realmadridmanagersystem290322.entity.Player;
import com.example.realmadridmanagersystem290322.repository.ClubRepository;
import com.example.realmadridmanagersystem290322.repository.LeagueRepository;
import com.example.realmadridmanagersystem290322.service.ClubService;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
    private final ModelMapper modelMapper;

    public ClubServiceImpl(ClubRepository clubRepository,
                           ModelMapper modelMapper) {
        this.clubRepository = clubRepository;
        this.modelMapper = modelMapper;
    }

    // League: entity -> dto (modelmapper)
    private LeagueDto leagueEntityToDto(League league) {
        return this.modelMapper.map(league, LeagueDto.class);
    }

    // Player: entity -> dto
    private PlayerDto playerEntityToDto(Player pLayer) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        PlayerDto playerDto = new PlayerDto();

        playerDto.setPlayerId(pLayer.getPlayerId());
        playerDto.setName(pLayer.getName());
        playerDto.setDateOfBirth(simpleDateFormat.format(pLayer.getDateOfBirth()));
        playerDto.setCountry(pLayer.getCountry());
        playerDto.setHeight(pLayer.getHeight());
        playerDto.setPosition(pLayer.getPosition());
        playerDto.setJoinedDate(simpleDateFormat.format(pLayer.getJoinedDate()));

        return playerDto;
    }

    // Club dto -> entity (modelmapper)
    private Club mapDtoToEntity(ClubDto clubDto) {
        return this.modelMapper.map(clubDto, Club.class);
    }

    // Club entity -> dto (modelmapper)
    private ClubDto mapEntityToDto(Club club) {
        return this.modelMapper.map(club, ClubDto.class);
    }

    private ClubDto clubEntityToDto(Club club) {
        ClubDto clubDto = new ClubDto();

        clubDto.setClubId(club.getClubId());
        clubDto.setName(club.getName());
        clubDto.setCountry(club.getCountry());
        clubDto.setSeason(club.getSeason());

        clubDto.setLeagues(club.getLeagues()
                .stream()
                .map(league -> leagueEntityToDto(league))
                .collect(Collectors.toSet()));

        clubDto.setPlayers(club.getPlayers()
                .stream()
                .map(player -> {
                    try {
                        return playerEntityToDto(player);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toSet()));

        return clubDto;
    }

    // 1, Save new club
    @Override
    public ClubDto saveNewClub(ClubDto clubDto) {

        Club club = mapDtoToEntity(clubDto);

        Club newClub = this.clubRepository.save(club);

        return mapEntityToDto(newClub);
    }

    // 2, Get all clubs
    @Override
    public List<ClubDto> getAllClubDtos() {
        List<Club> clubs = this.clubRepository.findAll();
        List<ClubDto> clubDtos = clubs.stream()
                .map(c -> clubEntityToDto(c))
                .collect(Collectors.toList());
        return clubDtos;
    }

    // 3, Get a club by season
    @Override
    public ClubDto findClubBySeason(String season) {
        Club findBySeason = this.clubRepository.findBySeason(season);

        return clubEntityToDto(findBySeason);
    }

    // **, Check club exists or not
    @Override
    public boolean checkClubExistsBySeason(String season) {
        if(clubRepository.existsBySeason(season)) {
            return true;
        }
        return false;
    }

    // Delete a club by season
    public void deleteCLubBySeason(String season) {
        Club clubFindedBySeason = this.clubRepository.findBySeason(season);
        this.clubRepository.delete(clubFindedBySeason);
    }
}
