package com.example.realmadridmanagersystem290322.controller;

import com.example.realmadridmanagersystem290322.dto.PlayerDto;
import com.example.realmadridmanagersystem290322.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/player")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    // Save a new player to an existing club
    @PostMapping(path = "/saveNewPlayer/{seasonOfClub}")
    public ResponseEntity<PlayerDto> saveNewPlayer(
            @RequestBody PlayerDto playerDto,
            @PathVariable(value = "seasonOfClub") String seasonOfClub) throws Exception {
        return new ResponseEntity<>(this.playerService.saveNewPlayer(playerDto, seasonOfClub), HttpStatus.CREATED);
    }

    // Save an existing player to an existing club
    @GetMapping(path = "/saveExistPlayerToClub/{clubId}/{playerId}")
    public ResponseEntity<String> saveExistPLayerToExistClub(@PathVariable(value = "clubId") Long clubId,
                                                             @PathVariable(value = "playerId") Long playerId) {
        if(this.playerService.checkForSaveExistPLayerToExistClub(clubId, playerId)) {
            this.playerService.saveExistPLayerToExistClub(clubId, playerId);
            return new ResponseEntity<>("Save player successfully !!!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Save player fail !!!", HttpStatus.BAD_REQUEST);
    }

    // Delete an existing player from an existing club
    @GetMapping(path = "/deleteExistPlayerFromClub/{clubId}/{playerId}")
    public ResponseEntity<String> deleteExistPlayerFromExistClub(@PathVariable(value = "clubId") Long clubId,
                                                                 @PathVariable(value = "playerId") Long playerId) {
        if(this.playerService.checkForDeleteExistPlayerFromClub(clubId, playerId)) {
            this.playerService.deleteExistPlayerFromClub(clubId, playerId);
            return new ResponseEntity<>("Delete player successfully !!!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Delete player failed !!!", HttpStatus.BAD_REQUEST);
    }

    // Get all existing players
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/getAllPLayersAllTime")
    public ResponseEntity<List<PlayerDto>> getAllPLayersAllTime(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "ascending", required = false) String sortDirection) {
        List<PlayerDto> players = this.playerService.getAllPLayersAllTime(pageNo, pageSize, sortBy, sortDirection);

        return new ResponseEntity<>(players, HttpStatus.OK);
    }
}
