import { Component, Input } from '@angular/core'
import {
  InformationOverlayComponent
} from '../information-overlay/information-overlay.component'
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog'
import { Horse } from '../../../../models/horse/Horse'
import { Jockey } from '../../../../models/jockey/Jockey'

@Component({
  selector: 'app-horse-jockey-info',
  templateUrl: './horse-jockey-info.component.html',
  styleUrls: ['./horse-jockey-info.component.css']
})
export class HorseJockeyInfoComponent {

  @Input() horse!: Horse
  @Input() jockey!: Jockey

  ref: DynamicDialogRef | undefined;

  constructor(public dialogService: DialogService) {}

  showInformation (id: number, type: 'jockey' | 'horse') {
    const isSmallScreen = window.innerWidth < 768;
    const width = isSmallScreen ? '90%' : '50%';

    this.ref = this.dialogService.open(InformationOverlayComponent, {
        data: { id: id, type: type },
        width: width,
        contentStyle: { overflow: 'auto' },
        baseZIndex: 10000
      }
    );
  }
}
