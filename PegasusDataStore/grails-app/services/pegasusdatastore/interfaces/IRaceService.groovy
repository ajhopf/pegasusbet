package pegasusdatastore.interfaces

import model.dtos.raceDTOs.RaceRequestDTO
import pegasusdatastore.Race

interface IRaceService {
    List<Race> list(Map args)
    Race save(RaceRequestDTO raceRequestDTO)
}