import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TableColumnDefinition } from "../../../../models/table/TableColumnDefinition";
import { Horse } from '../../../../models/horse/Horse'

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableComponent {
  @Input() tableHeader: string = ''
  @Input() tableColumnDefinition: TableColumnDefinition[] = []
  @Input() values: any[] = []

  @Output() filterEvent = new EventEmitter()
  @Output() rowClickEvent = new EventEmitter()
  filterValue = ''

  handleFilterEvent(filterValue: string) {
    this.filterEvent.emit(filterValue)
  }

  handleRowClickEvent(value: any) {
    console.log(value)
    this.rowClickEvent.emit(value)
  }
}
