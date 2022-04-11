package com.example.realmadridmanagersystem290322.service.serviceImpl;

import com.example.realmadridmanagersystem290322.dto.PlayerDto;
import com.example.realmadridmanagersystem290322.entity.Club;
import com.example.realmadridmanagersystem290322.entity.Player;
import com.example.realmadridmanagersystem290322.repository.ClubRepository;
import com.example.realmadridmanagersystem290322.repository.PlayerRepository;
import com.example.realmadridmanagersystem290322.service.PlayerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final ClubRepository clubRepository;
    private final ModelMapper modelMapper;

    // player: entity to dto
    private PlayerDto mapEntityToDto(Player pLayer) throws Exception {
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

    // player: dto to entity
    private Player mapDtoToEntity(PlayerDto playerDto) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        Player player = new Player();

        player.setName(playerDto.getName());
        player.setDateOfBirth(simpleDateFormat.parse(playerDto.getDateOfBirth()));
        player.setCountry(playerDto.getCountry());
        player.setHeight(playerDto.getHeight());
        player.setPosition(playerDto.getPosition());
        player.setJoinedDate(simpleDateFormat.parse(playerDto.getJoinedDate()));

        return player;
    }

    // Save new player
    @Override
    public PlayerDto saveNewPlayer(PlayerDto playerDto, String seasonOfClub) throws Exception {
        Player player = mapDtoToEntity(playerDto);
        Set<Club> clubs = new HashSet<>();

        Club findClubBySeason = this.clubRepository.findBySeason(seasonOfClub);
        Set<Player> players = findClubBySeason.getPlayers();

        clubs.add(findClubBySeason);
        player.setClubs(clubs);

        Player savePlayer = this.playerRepository.save(player);

        players.add(savePlayer);
        findClubBySeason.setPlayers(players);
        this.clubRepository.save(findClubBySeason);

        return mapEntityToDto(savePlayer);
    }

    @Override
    public List<PlayerDto> getAllPLayersAllTime(int pageNo, int pageSize, String sortBy, String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Player> playerPage = this.playerRepository.findAll(pageable);
        List<Player> playerList = playerPage.getContent();

        // List<Player> players = this.playerRepository.findAll();
        List<PlayerDto> playerDtos = playerList.stream()
                .map(player -> {
                    try {
                        return mapEntityToDto(player);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());
        return playerDtos;
    }

    // **
    @Override
    public boolean checkForSaveExistPLayerToExistClub(Long clubId, Long playerId) {
        Club club = this.clubRepository.findById(clubId).orElse(null);
        Player player = this.playerRepository.findById(playerId).orElse(null);

        boolean value = false;
        if(club != null && player != null) {
            Set<Player> players = club.getPlayers();
            Player checkPLayer = players.stream()
                    .filter(p -> p.getPlayerId().equals(playerId))
                    .findFirst()
                    .orElse(null);

            if(checkPLayer == null) {
                value = true;
            } else {
                value = false;
            }
        }

        if(club == null || player == null || value == false) {
            return false;
        }
        return true;
    }

    // **
    @Override
    public boolean checkForDeleteExistPlayerFromClub(Long clubId, Long playerId) {
        Club club = this.clubRepository.findById(clubId).orElse(null);
        Player player = this.playerRepository.findById(playerId).orElse(null);

        boolean value = false;
        if(club != null && player != null) {
            Player checkPlayer = club.getPlayers()
                    .stream()
                    .filter(p -> p.getPlayerId().equals(playerId))
                    .findFirst()
                    .orElse(null);

            if(checkPlayer == null) {
                value = false;
            } else {
                value = true;
            }
        }

        if(club == null || player == null || value == false) {
            return false;
        }
        return true;
    }

    // Save an existing player to an existing club
    @Override
    public void saveExistPLayerToExistClub(Long clubId, Long playerId) {
        if(checkForSaveExistPLayerToExistClub(clubId, playerId)) {
            Club club = this.clubRepository.findById(clubId).get();
            Player player = this.playerRepository.findById(playerId).get();

            Set<Player> players = club.getPlayers();
            players.add(player);
            club.setPlayers(players);

            this.clubRepository.save(club);
        }
    }

    // Delete an existing player from an existing club
    @Override
    public void deleteExistPlayerFromClub(Long clubId, Long playerId) {
        if(checkForDeleteExistPlayerFromClub(clubId, playerId)) {
            Club club = this.clubRepository.findById(clubId).get();
            Player player = this.playerRepository.findById(playerId).get();

            Set<Player> players = club.getPlayers();
            players.remove(player);
            club.setPlayers(players);

            this.clubRepository.save(club);
        }
    }
}
