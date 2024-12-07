package model.dtos.raceDTOs

class RaceResponseDTO  {
    Long id
    Long raceCourseId
    String time
    String date
    List<RaceHorseJockeyDTO> raceHorseJockeys
}
