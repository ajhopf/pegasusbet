package model.dtos.horseDTOs

class HorseResponseDTO {
    Long id
    String name
    String age
    String sex
    Integer numberOfRaces
    Integer numberOfVictories
    List<HorseResultResponseDTO> horseResults
}
