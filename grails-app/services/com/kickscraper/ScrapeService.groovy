package com.kickscraper

import geb.Browser
import org.codehaus.groovy.grails.plugins.codecs.URLCodec

class ScrapeService {

    static browser = new Browser()

    def search(term) {
        processSearchPage "http://www.kickstarter.com/projects/search?term=${ URLCodec.encode(term) }"
    }

    def rewards(url) {
        processRewardsPage url
    }

    private def processRewardsPage(url) {

        def pledgeLevels = []

        browser.drive {
            go url

            $('.NS-projects-reward.clickable').each { element ->
                def pledgeLevel = [:]
                pledgeLevel.title = element.find('h3').text()
                pledgeLevel.backers = element.find('.backers-limits span.num-backers').text()
                pledgeLevel.limit = element.find('.backers-limits span.limited')?.text()
                pledgeLevel.delivery = element.find('.delivery-date').text()
                pledgeLevel.description = element.find('.desc').text()
                pledgeLevels << pledgeLevel
            }
        }

        pledgeLevels
    }

    private def processSearchPage(url) {

        def items = []

        browser.drive {

            go url

            $('li.project').collect { element ->

                def date = element.find('li.ksr_page_timer').@'data-end_time'

                if (date) {

                    def item = [:]
                    def titleElement = element.find('h2')
                    item.link = titleElement.find('a').first().@href
                    item.title = titleElement.find('a').text()
                    item.date = date
                    items << item
                }
            }

            if ($('div.pagination') && !url.contains('page=')) {
                def numberOfPages = $('div.pagination a')[-2].@href.split('page=')[1].split('&')[0]

                (2..(numberOfPages as int)).each {
                    items += processSearchPage url.replace('term=', 'page=' + it + '&term=')
                }
            }
        }

        items
    }

}
