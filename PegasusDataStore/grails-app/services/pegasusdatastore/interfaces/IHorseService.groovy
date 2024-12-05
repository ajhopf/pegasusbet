package pegasusdatastore.interfaces

import model.dtos.HorseDTO
import pegasusdatastore.Horse

interface IHorseService {
    List<HorseDTO> list(Map args)
    Horse save(Horse horse)
    HorseDTO getHorse(Long id)
    boolean deleteHorse(Serializable id)
    Number count()
}