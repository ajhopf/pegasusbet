import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TableColumnDefinition } from "../../../../models/table/TableColumnDefinition";

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html'
})
export class TableComponent {
  @Input() tableHeader: string = ''
  @Input() tableColumnDefinition: TableColumnDefinition[] = []
  @Input() values: any[] = []
  @Input() filterFn: any
  @Output() deleteEvent = new EventEmitter()
  @Output() editEvent = new EventEmitter()
  @Output() filterEvent = new EventEmitter()
  filterValue = ''

  handleEditEvent(id: string) {
    this.editEvent.emit(id)
  }

  handleDeleteEvent(id: string) {
    this.deleteEvent.emit(id)
  }

  handleFilterEvent(filterValue: string) {
    this.filterEvent.emit(filterValue)
  }
}
