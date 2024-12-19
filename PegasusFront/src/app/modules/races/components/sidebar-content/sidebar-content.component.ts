import { Component, Input } from '@angular/core';
import { RaceHorseJockey } from "../../../../models/races/RaceHorseJockey";

@Component({
  selector: 'app-sidebar-content',
  templateUrl: './sidebar-content.component.html',
  styleUrls: ['./sidebar-content.component.css']
})
export class SidebarContentComponent {
  @Input() horseJockey: RaceHorseJockey | undefined
}
