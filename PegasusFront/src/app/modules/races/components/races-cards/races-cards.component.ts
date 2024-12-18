import { Component, Input, OnInit } from '@angular/core';
import { Race } from "../../../../models/races/Race";

@Component({
  selector: 'app-races-cards',
  templateUrl: './races-cards.component.html',
  styleUrls: ['./races-cards.component.css']
})
export class RacesCardsComponent {
  @Input() race!: Race
}
