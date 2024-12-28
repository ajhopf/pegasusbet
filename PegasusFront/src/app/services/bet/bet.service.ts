import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs'
import { environment } from '../../../environments/environment'
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { CookieService } from 'ngx-cookie-service'
import { WalletResponse } from '../../models/wallet/WalletResponse'
import { TransactionRequest } from '../../models/wallet/TransactionRequest'
import { TokenFields } from '../../models/auth/TokenFields'
import { GetUserBetsResponse } from '../../models/bet/GetUserBetsResponse'
import { CreateBetRequest } from '../../models/bet/CreateBetRequest'
import { CreateBetResponse } from '../../models/bet/CreateBetResponse'

@Injectable({
  providedIn: 'root'
})
export class BetService {

  private API_URL = environment.USERS_API

  constructor(
    private httpClient: HttpClient,
    private cookie: CookieService) {
  }

  getUserBets(): Observable<GetUserBetsResponse> {
    return this.httpClient.get<any>(`${this.API_URL}/bets`, this.getHttpOptions())
  }

  createBet(createBetRequest: CreateBetRequest): Observable<CreateBetResponse> {
    return this.httpClient.post<any>(`${this.API_URL}/bets`,
      createBetRequest,
      this.getHttpOptions())
  }

  updateBetViewStatus() {
    return this.httpClient.put<any>(`${this.API_URL}/bets/status`,{}, this.getHttpOptions())
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
