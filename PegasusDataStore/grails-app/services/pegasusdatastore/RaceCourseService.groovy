package pegasusdatastore

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import model.dtos.RaceCourseDTO
import model.mappers.RaceCourseMapper
import pegasusdatastore.interfaces.IRaceCourseService

@Service(RaceCourse)
abstract class RaceCourseService implements IRaceCourseService {
    @Override
    @Transactional
    List<RaceCourseDTO> list(Map params) {
        List<RaceCourse> raceCourses = RaceCourse.list(offset: params.offset, max: params.max)
        return RaceCourseMapper.toDTOs(raceCourses)
    }

    @Override
    @Transactional
    RaceCourseDTO getRaceCourse(Long id) {
        RaceCourse raceCourse = RaceCourse.get(id)
        return RaceCourseMapper.toDTO(raceCourse)
    }

    @Override
    @Transactional
    boolean deleteRaceCourse(Serializable id){
        RaceCourse raceCourse = RaceCourse.get(id)

        if (raceCourse == null) {
            return false
        } else {
            raceCourse.delete()
            return true
        }
    }
}
