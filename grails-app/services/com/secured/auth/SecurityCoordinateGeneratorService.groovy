package com.secured.auth

import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import grails.gorm.services.Service
import grails.gorm.transactions.Transactional

@Transactional
class SecurityCoordinateGeneratorService implements ICoordinateService, GrailsConfigurationAware {
    private static Random r = new Random()
    private static List<String> coordinatePositions

    @Override
    Map<String,String> generateCoordinates() {
        Map<String,String> securityCard = [:]
        for(coordinate in coordinatePositions)
            securityCard.put(coordinate,convert(r.nextInt(100)))
        while(!(uniqueness(securityCard) > securityCard.size()*0.75))
        {
            Collections.shuffle(coordinatePositions)
            for(coordinate in coordinatePositions)
                securityCard.coordinate= convert(r.nextInt(100))
        }
        return securityCard
    }

    @Override
    def uniqueness(Map coordinates) {
        def securityCardList = SecurityCoordinate.list()
        int matches = 0;
        for(card in securityCardList)
        {
            int localmatches = 0;
            for(entry in card)
            {
                if (entry.value == coordinates[entry.position])
                    ++localmatches;
            }
            if(localmatches > matches)
                matches = localmatches;
        }
        return coordinates.size() - matches;
    }
    private static String convert(int n)
    {
        if(n>0 && n<10)
            return new StringBuilder('0').append(n)
        return n.toString()
    }

    @Override
    void setConfiguration(Config co) {
        coordinatePositions = co.getProperty('security.coordinate.positions', List, []) as List<String>
    }
}
