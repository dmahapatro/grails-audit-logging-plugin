package test

import grails.core.GrailsApplication
import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class AuditColumnNamesSpec extends Specification {
    @Autowired
    GrailsApplication grailsApplication

    def setup() {
        Planet.auditable = true
    }

    def "test that the actual table name is logged in audit table instead of the property name"() {
        given:
        grailsApplication.config.grails.plugin.auditLog.logColumnNameAsPropertyName = true

        when:
        new Planet(
            name: "Earth",
            star: 'Sun',
            numOfSatellites: 1
        ).save(flush: true, failOnError: true)

        then:
        List<AuditTrail> trails = AuditTrail.findAllByClassName('test.Planet')
        def titleTrail = trails.find { it.propertyName == 'PLANET_NAME' }

        with(titleTrail) {
            oldValue == null
            newValue == 'Earth'
        }
    }
}
