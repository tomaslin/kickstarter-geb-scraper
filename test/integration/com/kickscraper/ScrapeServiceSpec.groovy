package com.kickscraper

import spock.lang.Specification
import betamax.Recorder
import org.junit.Rule
import betamax.Betamax
import grails.test.mixin.TestFor

@TestFor(ScrapeService)
class ScrapeServiceSpec extends Specification{

    @Rule Recorder recorder = new Recorder()

    @Betamax(tape='search')
    def "search returns correct information"(){

    }

    @Betamax(tape='minidrive')
    def "can isolate the correct pledge levels"(){

    }

}
