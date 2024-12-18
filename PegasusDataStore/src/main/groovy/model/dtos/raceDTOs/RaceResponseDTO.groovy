package model.dtos.raceDTOs

import model.dtos.RaceCourseDTO

class RaceResponseDTO  {
    Long id
    RaceCourseDTO raceCourse
    String time
    String date
    List<RaceHorseJockeyDTO> raceHorseJockeys
}
