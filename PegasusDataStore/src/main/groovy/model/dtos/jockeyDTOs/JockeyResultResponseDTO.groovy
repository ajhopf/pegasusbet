package model.dtos.jockeyDTOs

import pegasusdatastore.JockeyResults

class JockeyResultResponseDTO {
    String result // Exemplo: "1/10"
    String resultDate // Exemplo: "2024-12-01"

    JockeyResultResponseDTO(JockeyResults jockeyResults) {
        this.result = jockeyResults.result
        this.resultDate = jockeyResults.date.toString() // Converter para String, se necessário
    }
}
