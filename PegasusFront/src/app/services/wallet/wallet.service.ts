import { Injectable } from '@angular/core'
import { environment } from '../../../environments/environment'
import { TokenFields } from '../../models/auth/TokenFields'
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { CookieService } from 'ngx-cookie-service'
import { BehaviorSubject, Observable } from 'rxjs'
import { WalletResponse } from '../../models/wallet/WalletResponse'
import { TransactionRequest } from '../../models/wallet/TransactionRequest'

@Injectable({
  providedIn: 'root'
})
export class WalletService {
  private userMoneySource = new BehaviorSubject<number>(0)
  userMoney$ = this.userMoneySource.asObservable()

  private API_URL = environment.USERS_API

  constructor(
    private httpClient: HttpClient,
    private cookie: CookieService) {
  }

  updateUserMoney(amount: number): void {
    this.userMoneySource.next(amount)
  }

  fetchUserWalletInfo(): Observable<WalletResponse> {
    const USERNAME = this.cookie.get(TokenFields.USERNAME)

    return this.httpClient.get<any>(`${this.API_URL}/wallet/${USERNAME}`, this.getHttpOptions())
  }

  addTransaction(transactionRequest: TransactionRequest): Observable<WalletResponse> {
    const USERNAME = this.cookie.get(TokenFields.USERNAME)

    return this.httpClient.post<any>(`${this.API_URL}/wallet/transaction/${USERNAME}`,
      transactionRequest,
      this.getHttpOptions())
  }

  getHttpOptions() {
    const JWT_TOKEN = this.cookie.get(TokenFields.ACCESS_TOKEN)

    return {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${JWT_TOKEN}`
      })
    }
  }
}
