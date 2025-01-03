import { Component, OnDestroy, OnInit } from '@angular/core';

import { BetService } from "../../../../../services/bet/bet.service";
import { Subject, take, takeUntil } from "rxjs";
import { Bet } from "../../../../../models/bet/Bet";
import { Router } from "@angular/router";
import { NotificationsService } from "../../../../../services/notification/notifications.service";
import { MessageService } from 'primeng/api'

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent implements OnInit, OnDestroy {
  bets: Bet[] | undefined
  private destroy$ = new Subject<void>();

  constructor(
    private betService: BetService,
    private notificationService: NotificationsService,
    private router: Router,
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.fetchUserBets()

    this.notificationService.refreshNotifications$
      .pipe(takeUntil(this.destroy$))
      .subscribe(() => {
        this.fetchUserBets()
      })
  }

  fetchUserBets() {
    this.betService.getUserBets()
      .pipe(take(1))
      .subscribe({
        next: value => {
          this.bets = this.filterUnseenBets(value.bets)

          if (this.bets.length > 0) {
            this.bets.filter(bet => {
              const severity = bet.status == 'WIN' ? 'success' : 'danger'
              const summary = bet.status == 'WIN' ? 'Aposta ganha!' : 'Infelizmente não foi dessa vez...'

              this.messageService.add({
                severity: severity,
                summary: summary
              })
            })
          }
        },
        error: err => {
          console.log(err)
        }
      })
  }

  filterUnseenBets(bets: Bet[]) {
    return bets.filter(bet => {
      return !bet.resultViewed && bet.status !== 'WAITING'
    })
  }

  handleViewNotification() {
    this.router.navigate(['/races/bets'])
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
