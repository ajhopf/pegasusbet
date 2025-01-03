import { Injectable } from '@angular/core'
import { environment } from '../../../environments/environment'
import { TokenFields } from '../../models/auth/TokenFields'
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { CookieService } from 'ngx-cookie-service'
import { BehaviorSubject, Observable } from 'rxjs'
import { WalletResponse } from '../../models/wallet/WalletResponse'
import { TransactionRequest } from '../../models/wallet/TransactionRequest'
import { UserWalletTransactionsResponse } from '../../models/wallet/UserWalletTransactionsResponse'

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
    return this.httpClient.get<any>(`${this.API_URL}/wallet`, this.getHttpOptions())
  }

  fetchUserWalletTransactions(): Observable<UserWalletTransactionsResponse> {
    return this.httpClient.get<any>(`${this.API_URL}/wallet/transactions`, this.getHttpOptions())
  }

  addTransaction(transactionRequest: TransactionRequest): Observable<WalletResponse> {
    return this.httpClient.post<any>(`${this.API_URL}/wallet`,
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
