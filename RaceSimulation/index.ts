import express from 'express';
import {WebSocketServer} from 'ws';
import cors from 'cors';
import {produceRaceResults} from "./services/kafka";
import { StartRace } from './models/startRace'

// Configuração do servidor HTTP
const app = express();
const PORT = 8085;

// Middleware CORS para permitir o frontend se conectar
app.use(cors({ origin: 'http://localhost:4200', credentials: true }));

// Rota para verificar se o servidor está ativo
app.get('/', (req, res) => {
    res.send('Race Simulation Service is running');
});

// Inicia o servidor HTTP
const server = app.listen(PORT, () => {
    console.log(`Servidor rodando em http://localhost:${PORT}`);
});

// Configuração do WebSocket
const wss = new WebSocketServer({ server });

wss.on('connection', (ws) => {
    console.log('Cliente conectado ao WebSocket');

    let participants = []
    let raceId = null
    let interval = null

    ws.on('message', (data: string) => {
        const message: StartRace = JSON.parse(data);

        try {
            if (message.type === 'startRace') {
                console.log('Dados recebidos para iniciar corrida:', message);

                // Configura os participantes e o ID da corrida
                participants = message.participants.map((p) => ({
                    raceHorseJockeyId: p.raceHorseJockeyId,
                    position: 0,
                    odds: p.odds,
                }));
                raceId = message.raceId;

                wss.clients.forEach((client) => {
                    client.send(JSON.stringify({
                        raceId,
                        positions: participants
                    }));
                })

                // Inicia a corrida
                interval = setInterval(() => {
                    participants.forEach((participant) => {
                        const speed = Math.random() * (1 / (participant.odds)) * 5; // Velocidade baseada nas odds
                        participant.position += speed;
                    });

                    const raceUpdate = {
                        raceId,
                        positions: participants,
                    };

                    wss.clients.forEach((client) => {
                        client.send(JSON.stringify(raceUpdate));
                    })

                    // Finaliza a corrida após 100 unidades
                    if (participants.some((p) => p.position >= 3)) {
                        clearInterval(interval);
                        console.log('Corrida finalizada');

                        produceRaceResults({raceId: raceId, positions: participants})
                    }
                }, 2000);
            }
        } catch (err) {
            console.error('Erro ao processar mensagem:', err);
        }
    })

    ws.on('close', () => {
        console.log('Cliente desconectado');
        // clearInterval(interval);
    });
});