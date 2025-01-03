package model.mappers

import model.dtos.oddsDTOs.OddsResponseDTO
import pegasusdatastore.Odds

import java.time.format.DateTimeFormatter

class OddsMapper {
    static OddsResponseDTO toResponseDTO(Odds odds) {
        return new OddsResponseDTO(
                raceHorseJockeyId: odds.raceHorseJockey.id,
                rating: odds.rating,
                initialOdd: odds.initialOdd,
                currentOdd: odds.currentOdd,
                lastUpdated:  odds.lastUpdated?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        )
    }

    static List<OddsResponseDTO> toDTOs(List<Odds> odds) {
        return odds.collect { toResponseDTO(it) }
    }
}
