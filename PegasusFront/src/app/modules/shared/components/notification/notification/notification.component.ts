import { Component, OnInit } from '@angular/core';

import { BetService } from "../../../../../services/bet/bet.service";
import { take } from "rxjs";
import { Bet } from "../../../../../models/bet/Bet";
import { Router } from "@angular/router";

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent implements OnInit {
  bets: Bet[] | undefined

  constructor(
    private betService: BetService,
    private router: Router
  ) {}

  ngOnInit() {
    this.fetchUserBets()
  }

  fetchUserBets() {
    this.betService.getUserBets()
      .pipe(take(1))
      .subscribe({
        next: value => {
          console.log(value.bets)
          this.bets = this.filterUnseenBets(value.bets)
          console.log(this.bets)
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

}
