import { Injectable } from '@angular/core';
import { Subject } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class NotificationsService {
  private refreshNotificationsSubject = new Subject<void>();

  refreshNotifications$ = this.refreshNotificationsSubject.asObservable();

  triggerRefreshNotifications() {
    this.refreshNotificationsSubject.next();
  }
}
