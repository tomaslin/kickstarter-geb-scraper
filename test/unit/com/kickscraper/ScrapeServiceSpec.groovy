package com.kickscraper

import betamax.Betamax
import betamax.Recorder
import com.gargoylesoftware.htmlunit.ProxyConfig
import grails.test.mixin.TestFor
import org.junit.Rule
import spock.lang.Specification

@TestFor(ScrapeService)
class ScrapeServiceSpec extends Specification {

    @Rule Recorder recorder = new Recorder()

    def setup() {
        def proxyConfig = new ProxyConfig("localhost", 5555)
        proxyConfig.addHostsToProxyBypass("localhost")
        service.browser.driver.webClient.proxyConfig = proxyConfig
    }

    @Betamax(tape = 'search')
    def "search returns correct information"() {
        expect:
        service.search('ipad').size() == 32
    }

    @Betamax(tape = 'rewards')
    def "can isolate the correct pledge levels"() {

        expect:
        service.rewards(url).size() == expected

        where:
        url                                                                                                  | expected
        'http://www.kickstarter.com/projects/1342319572/the-nifty-minidrive?ref=live'                        | 12
        'http://www.kickstarter.com/projects/597507018/pebble-e-paper-watch-for-iphone-and-android?ref=live' | 0

    }

}
