import { Component, Input, OnInit } from '@angular/core';
import { CookieService } from "ngx-cookie-service";
import { TokenFields } from "../../../../models/auth/TokenFields";
import { HorseService } from "../../../../services/horse/horse.service";
import { JockeyService } from "../../../../services/jockey/jockey.service";
import { take } from "rxjs";
import { MessageService } from "primeng/api";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit{

  username: string | undefined
  isSearchingHorses: boolean = false
  isSearchingJockeys: boolean = false

  constructor(
    private cookieService: CookieService,
    private horseService: HorseService,
    private jockeyService: JockeyService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.username = this.cookieService.get(TokenFields.USERNAME)
  }

  insertHorses() {
    this.isSearchingHorses = true

    this.messageService.add({
      severity: 'info',
      summary: 'Procurando novos cavalos...'
    })

    this.horseService.insertHorsesViaWebcrawler()
      .pipe(take(1))
      .subscribe({
        next: value => {
          this.isSearchingHorses = false

          this.messageService.add({
            severity: 'success',
            summary: 'Crawler completado com sucesso!'
          })

          console.log(value)
        },
        error: err => {
          this.isSearchingJockeys = false

          this.messageService.add({
            severity: 'error',
            summary: 'Houve uma falha durante o buscador de cavalos'
          })

          console.log(err)
        }
      })
  }

  insertJockeys() {
    this.isSearchingJockeys = true

    this.messageService.add({
      severity: 'info',
      summary: 'Procurando novos jockeys...',
    })

    this.jockeyService.insertJockeysViaWebcrawler()
      .pipe(take(1))
      .subscribe({
        next: value => {
          this.isSearchingJockeys = false

          this.messageService.add({
            severity: 'success',
            summary: 'Buscador de jockeys completado com sucesso!'
          })

          console.log(value)
        },
        error: err => {
          this.isSearchingJockeys = false

          this.messageService.add({
            severity: 'error',
            summary: 'Houve uma falha durante o buscador de jockeys'
          })

          console.log(err)
        }
      })
  }



}
