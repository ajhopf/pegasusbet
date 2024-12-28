import { Injectable } from '@angular/core';
import { Subject } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class NotificationsService {
  private refreshNotificationsSubject = new Subject<void>();

  // Observable para que outros componentes possam escutar
  refreshNotifications$ = this.refreshNotificationsSubject.asObservable();

  // Método para emitir o evento de atualização
  triggerRefreshNotifications() {
    this.refreshNotificationsSubject.next();
  }
}
