import { Component, EventEmitter, Output } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { take } from 'rxjs'
import { MessageService } from 'primeng/api'

@Component({
  selector: 'app-money-conversion',
  templateUrl: './money-conversion.component.html',
  styleUrls: ['./money-conversion.component.css']
})
export class MoneyConversionComponent {
  @Output() onInputChange: EventEmitter<any> = new EventEmitter()

  amountInput: number = 0
  selectedCoin: string = 'BRL'

  API_URL = 'https://economia.awesomeapi.com.br/last'
  conversionRate: number = 1

  paymentOptions: any[] = [
    {name: 'Real', value: 'BRL'},
    {name: 'Dólar Americano', value: 'USD'},
    {name: 'Dólar Canadense', value: 'CAD'},
    {name: 'Euro', value: 'EUR'},
    {name: 'Peso Argentino', value: 'ARS'},
    {name: 'Bitcoin', value: 'BTC'}
  ]

  constructor(
    private http: HttpClient,
    private messageService: MessageService,
  ) {

  }

  handlePaymentChange() {
    const datakey = this.selectedCoin + 'BRL'

    if (this.selectedCoin !== 'BRL') {
      this.http.get<any>(`${this.API_URL}/${this.selectedCoin}-BRL`)
        .pipe(take(1))
        .subscribe({
          next: data => {
            if (data[datakey]) {
              this.conversionRate = data[datakey].high
              this.handleInputChange()
            }
          },
          error: err => {
            this.messageService.add({
              severity: 'error',
              summary: "Não foi possível obter a cotação",
              detail: `A API de conversão não conseguiu obter a cotação para a moeda ${this.selectedCoin}`
            })

            this.selectedCoin = 'BRL'
          }
        })
    } else {
      this.conversionRate = 1
      this.handleInputChange()
    }
  }

  handleInputChange() {
    this.onInputChange.emit(this.amountInput * this.conversionRate)
  }


}
