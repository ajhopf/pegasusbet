package model.dtos.jockeyDTOs

class JockeyResponseDTO {
    Long id
    String name
    Integer numberOfRaces
    Integer numberOfVictories
    List<JockeyResultResponseDTO> jockeyResults
}
