package at.fhj.catlicious.common

import spock.lang.Specification

/**
 * @author bnjm@harmless.ninja - 5/3/17.
 */
class AssertTest extends Specification {

    void "throws expected exception"() {
        when:
            Assert.notNull(null)
        then:
            thrown(IllegalArgumentException)
    }

    void "doesn't throw if not null"() {
        when:
            Assert.notNull("notNull")
        then:
            noExceptionThrown()
    }
}
