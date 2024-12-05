package pegasusdatastore.interfaces

import model.dtos.JockeyDTO
import pegasusdatastore.Jockey

interface IJockeyService {
    List<JockeyDTO> list(Map args)
    Jockey save(Jockey jockey)
    JockeyDTO getJockey(Long id)
    boolean deleteJockey(Serializable id)
    Number count()
}