package org.grails.prettytime

import org.joda.time.DateTime
import org.joda.time.Period

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(PrettyTimeTagLib)
class PrettyTimeTagLibTests extends Specification {

    private static final String MOMENTS_AGO = 'moments ago'
    private static final String ONE_DAY_AGO = '1 day ago'
    private static final String MOMENTS_AGO_CAPITALIZED = 'Moments ago'

    void 'format date'() {
        expect:
        applyTemplate('<prettytime:display />') == ''

        applyTemplate('<prettytime:display date="${new Date()}" />') == 'moments ago'
        applyTemplate('<prettytime:display date="${new Date() - 7}" />') == '1 week ago'
        applyTemplate('<prettytime:display date="${new Date() - 14}" />') == '2 weeks ago'

        when:
        def cal = Calendar.instance
        cal.add(Calendar.HOUR, -1)

        then:
        applyTemplate('<prettytime:display date="${date}" />', [date: cal.time]) == '1 hour ago'

        when:
        cal.add(Calendar.HOUR, 2)

        then:
        applyTemplate('<prettytime:display date="${date}" />', [date: cal.time]) == '60 minutes from now'
    }

    void 'format date with joda time'() {
        when:
        def dt = new DateTime()

        then:
        applyTemplate('<prettytime:display date="${date}" />', [date: dt]) == 'moments ago'

        when:
        dt = dt.minus(Period.days(7))

        then:
        applyTemplate('<prettytime:display date="${date}" />', [date: dt]) == '1 week ago'

        when:
        dt = dt.minus(Period.days(7))

        then:
        applyTemplate('<prettytime:display date="${date}" />', [date: dt]) == '2 weeks ago'
    }

    void 'format date with long'() {
        expect:
        applyTemplate('<prettytime:display date="${date}" />', [date: dateToTest]) ==
            applyTemplate('<prettytime:display date="${date}" />', [date: dateToTest.getTime()])

        where:
        dateToTest << [new Date(), new Date() - 7, new Date() - 14]
    }

    void 'with different locale'() {
        expect:
        applyTemplate('<prettytime:display date="${new Date()}" />') == 'moments ago'

        when:
        request.addPreferredLocale(Locale.GERMAN)

        then:
        applyTemplate('<prettytime:display date="${new Date()}" />') == 'gerade eben'
        applyTemplate('<prettytime:display date="${new Date() - 7}" />') == 'vor 1 Woche'
        applyTemplate('<prettytime:display date="${new Date() - 14}" />') == 'vor 2 Wochen'

        when:
        request.addPreferredLocale(new Locale('pl'))

        then:
        applyTemplate('<prettytime:display date="${new Date()}" />') == 'przed chwilą'
        applyTemplate('<prettytime:display date="${new Date() - 7}" />') == '1 tydzień temu'
        applyTemplate('<prettytime:display date="${new Date() - 14}" />') == '2 tygodni(e) temu'
    }

    void 'display'() {
        expect:
        applyTemplate('<prettytime:display date="${null}"/>') == ''

        applyTemplate('<prettytime:display date="${new Date()}"/>') == MOMENTS_AGO

        applyTemplate('<prettytime:display date="${new Date() - 1}"/>') == ONE_DAY_AGO
    }

    void 'capitalization'() {
        when:
        def date = new DateTime()

        then:
        applyTemplate('<prettytime:display date="${date}" capitalize="true"/>', [date: date]) == MOMENTS_AGO_CAPITALIZED
        applyTemplate('<prettytime:display date="${date}" capitalize="false"/>', [date: date]) == MOMENTS_AGO
        applyTemplate('<prettytime:display date="${date}" capitalize="${true}"/>', [date: date]) == MOMENTS_AGO_CAPITALIZED
        applyTemplate('<prettytime:display date="${date}" capitalize="${false}"/>', [date: date]) == MOMENTS_AGO
    }

    void 'joda-time'() {
        when:
        def date = new DateTime()

        then:
        applyTemplate('<prettytime:display date="${date}"/>', [date: date]) == MOMENTS_AGO
    }

    void 'show time'() {
        when:
        def date = new Date()
        String expectedOutput = MOMENTS_AGO + ', ' + date.format('hh:mm:ss a')

        then:
        applyTemplate('<prettytime:display date="${date}" showTime="true"/>', [date: date]) == expectedOutput
        applyTemplate('<prettytime:display date="${date}" showTime="${true}"/>', [date: date]) == expectedOutput
        applyTemplate('<prettytime:display date="${date}" showTime="false"/>', [date: date]) == MOMENTS_AGO
        applyTemplate('<prettytime:display date="${date}" showTime="${false}"/>', [date: date]) == MOMENTS_AGO
    }

    void 'show time with format'() {
        when:
        def date = new Date()

        then:
        applyTemplate('<prettytime:display date="${date}" showTime="true" format="HH:mm:ss"/>', [date: date]) ==
                  MOMENTS_AGO + ', ' + date.format('HH:mm:ss')
    }

    void 'show time in HTML5 time tag'() {
        when:
        def date = new Date()
        def dateString = applyTemplate('<g:formatDate date="${date}"/>', [date: date])
        def expectedOutput = """<time datetime="$dateString" title="$dateString">$MOMENTS_AGO</time>"""

        then:
        applyTemplate('<prettytime:display date="${date}" html5wrapper="true"/>', [date: date]) == expectedOutput
    }
}
