package model.dtos.horseDTOs

import pegasusdatastore.HorseResults

class HorseResultResponseDTO {
    String result // Exemplo: "1/10"
    String resultDate // Exemplo: "2024-12-01"

    HorseResultResponseDTO(HorseResults horseResult) {
        this.result = horseResult.result
        this.resultDate = horseResult.date.toString() // Converter para String, se necessário
    }
}
