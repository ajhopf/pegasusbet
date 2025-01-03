package pegasusdatastore.interfaces

import model.dtos.RaceCourseDTO
import pegasusdatastore.RaceCourse

interface IRaceCourseService {
    List<RaceCourseDTO> list(Map args)
    RaceCourse save(RaceCourse raceCourse)
    RaceCourseDTO getRaceCourse(Long id)
    boolean deleteRaceCourse(Serializable id)
    Number count()
}