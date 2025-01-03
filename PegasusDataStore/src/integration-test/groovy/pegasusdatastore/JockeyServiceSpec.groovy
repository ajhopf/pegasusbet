package pegasusdatastore

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class JockeyServiceSpec extends Specification {

    JockeyService jockeyService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Jockey(...).save(flush: true, failOnError: true)
        //new Jockey(...).save(flush: true, failOnError: true)
        //Jockey jockey = new Jockey(...).save(flush: true, failOnError: true)
        //new Jockey(...).save(flush: true, failOnError: true)
        //new Jockey(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //jockey.id
    }

    void "test get"() {
        setupData()

        expect:
        jockeyService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Jockey> jockeyList = jockeyService.list(max: 2, offset: 2)

        then:
        jockeyList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        jockeyService.count() == 5
    }

    void "test delete"() {
        Long jockeyId = setupData()

        expect:
        jockeyService.count() == 5

        when:
        jockeyService.delete(jockeyId)
        sessionFactory.currentSession.flush()

        then:
        jockeyService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Jockey jockey = new Jockey()
        jockeyService.save(jockey)

        then:
        jockey.id != null
    }
}
