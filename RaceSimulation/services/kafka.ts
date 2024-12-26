import {Kafka} from "kafkajs";
import { RaceResult } from '../models/raceResults'

const kafka = new Kafka({
    clientId: 'pegausbet-nodejs', // Identificador do cliente
    brokers: ['localhost:9092'] // Endereço do Kafka (substitua pelo seu broker)
})

const producer = kafka.producer()

const orderHorseJockeys = (raceResults: RaceResult) => {
    raceResults.positions.sort((a, b) => {
        return a.position - b.position
    })

    for(let i = 0; i < raceResults.positions.length; i++){
        raceResults.positions[i].result = i + 1 + "/" + raceResults.positions.length
    }

}

export const produceRaceResults = async (raceResults: RaceResult) => {
    await producer.connect()

    console.log(raceResults)

    orderHorseJockeys(raceResults)

    console.log(raceResults)

    await producer.send({
        topic: 'race-results', // Tópico para o qual você deseja enviar a mensagem
        messages: [
            { value: JSON.stringify(raceResults) }
        ],
    })

    await producer.disconnect()
}