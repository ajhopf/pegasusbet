package pegasusdatastore.interfaces

import model.dtos.jockeyDTOs.JockeyResponseDTO
import pegasusdatastore.Jockey

interface IJockeyService {
    List<JockeyResponseDTO> list(Map args)
    Jockey save(Jockey jockey)
    JockeyResponseDTO getJockey(Long id)
    boolean deleteJockey(Serializable id)
    Number count()
}