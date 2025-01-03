import { Injectable } from '@angular/core';
import { MessageService } from 'primeng/api'
import { RaceSimulationParticipant } from '../../models/races/RaceSimulationParticipant'
import { CookieService } from "ngx-cookie-service";
import { TokenFields } from "../../models/auth/TokenFields";

@Injectable({
  providedIn: 'root'
})
export class RaceSimulationService {
  private socket: WebSocket | undefined

  constructor(
    private messageService: MessageService,
    private cookieService: CookieService
  ) {
  }

  connect(): void {
    if (this.socket && this.socket.readyState === WebSocket.OPEN) {
      console.log('WebSocket já está conectado.');
      return;
    }

    this.socket = new WebSocket('ws://localhost:8085');

    this.socket.onopen = () => {
      console.log('WebSocket conectado com sucesso.');

      this.socket?.send(JSON.stringify(
        { type: 'register', username: this.cookieService.get(TokenFields.USERNAME) }
      ));

      this.messageService.add({
        severity: 'success',
        summary: 'Servidor conectado com sucesso'
      })
    };

    this.socket.onclose = () => {
      console.log('WebSocket desconectado.');

      this.messageService.add({
        severity: 'warn',
        summary: 'Servidor desconectado'
      })
    };

    this.socket.onerror = (error) => {
      console.error('Erro no WebSocket:', error);

      this.messageService.add({
        severity: 'error',
        summary: 'Falha ao conectar com o servidor'
      })
    };
  }

  startRace(raceId: number, participants: RaceSimulationParticipant[]): void {
    if (!this.socket || this.socket.readyState !== WebSocket.OPEN) {
      console.error('WebSocket não está conectado.');
      return;
    }

    const message = {
      type: 'startRace',
      raceId,
      participants
    };
    this.socket.send(JSON.stringify(message)); // Envia dados ao backend
  }

  onWebSocketUpdate(callback: (data: any) => void): void {
    if (!this.socket) {
      console.error('WebSocket não está conectado.');
      return;
    }

    this.socket.onmessage = (event) => {
      const data = JSON.parse(event.data);
        callback(data);
    };
  }

  closeConnection(): void {
    if (this.socket) {
      this.socket.close();
      this.socket = undefined;
    }
  }
}
