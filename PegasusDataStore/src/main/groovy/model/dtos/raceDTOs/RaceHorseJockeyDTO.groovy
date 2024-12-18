package model.dtos.raceDTOs

import model.dtos.horseDTOs.HorseResponseDTO
import model.dtos.jockeyDTOs.JockeyResponseDTO
import model.dtos.oddsDTOs.OddsResponseDTO
import model.mappers.HorseMapper
import model.mappers.JockeyMapper
import model.mappers.OddsMapper
import pegasusdatastore.RaceHorseJockey

class RaceHorseJockeyDTO {
    Long id
    HorseResponseDTO horse
    JockeyResponseDTO jockey
    OddsResponseDTO odds
    String position
    String raceTime

    RaceHorseJockeyDTO(RaceHorseJockey raceHorseJockey) {
        this.id = raceHorseJockey.id
        this.horse = HorseMapper.toResponseDTO(raceHorseJockey.horse)
        this.jockey = JockeyMapper.toResponseDTO(raceHorseJockey.jockey)
        this.position = raceHorseJockey.position
        this.raceTime = raceHorseJockey.raceTime
        this.odds = OddsMapper.toResponseDTO(raceHorseJockey.odds)
    }
}
