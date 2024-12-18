package pegasusdatastore

import grails.converters.JSON
import model.dtos.oddsDTOs.OddsResponseDTO

class OddsController {
    OddsService oddsService

    def index(Integer max, Integer offset, String filter, String filterField) {
        max = max ?: 1000
        offset = offset ?: 0

        if (!filterField) {
            filterField = 'name'
        }

        if (!filter) {
            filter = ''
        }

        List<OddsResponseDTO> oddsResponseDTOS = oddsService.list([max: max, offset: offset, filterField: filterField, filter: filter])

        render(status: 200, contentType: "application/json") {
            "odds" oddsResponseDTOS
            "items" oddsResponseDTOS.size()
            "offsetItems" offset
        }
    }
}
