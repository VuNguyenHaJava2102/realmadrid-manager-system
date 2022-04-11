package com.example.realmadridmanagersystem290322.service;

import com.example.realmadridmanagersystem290322.dto.PlayerDto;

import java.util.List;

public interface PlayerService {

    PlayerDto saveNewPlayer(PlayerDto playerDto, String seasonOfClub) throws Exception;

    List<PlayerDto> getAllPLayersAllTime(int pageNo, int pageSize, String sortBy, String sortDirection);

    void saveExistPLayerToExistClub(Long clubId, Long playerId);

    void deleteExistPlayerFromClub(Long clubId, Long playerId);

    // **
    boolean checkForSaveExistPLayerToExistClub(Long clubId, Long playerId);

    boolean checkForDeleteExistPlayerFromClub(Long clubId, Long playerId);
}
