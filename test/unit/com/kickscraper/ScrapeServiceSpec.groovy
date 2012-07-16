package com.kickscraper

import spock.lang.Specification
import betamax.Recorder
import org.junit.Rule
import betamax.Betamax
import grails.test.mixin.TestFor
import com.gargoylesoftware.htmlunit.ProxyConfig

@TestFor(ScrapeService)
class ScrapeServiceSpec extends Specification{

    @Rule Recorder recorder = new Recorder()

    def setup(){
        def proxyConfig = new ProxyConfig("localhost", 5555)
        proxyConfig.addHostsToProxyBypass("localhost")
        service.browser.driver.webClient.proxyConfig = proxyConfig
    }

    @Betamax(tape='search')
    def "search returns correct information"(){
        expect:
        service.search('ipad').size() == 32
    }

    @Betamax(tape='minidrive')
    def "can isolate the correct pledge levels"(){

    }

}
