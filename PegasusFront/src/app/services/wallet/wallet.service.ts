import { Injectable } from '@angular/core';
import { environment } from "../../../environments/environment";
import { TokenFields } from "../../models/auth/TokenFields";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { CookieService } from "ngx-cookie-service";
import { BehaviorSubject, Observable } from 'rxjs'
import { WalletResponse } from "../../models/wallet/WalletResponse";
import { TransactionRequest } from '../../models/wallet/TransactionRequest'

@Injectable({
  providedIn: 'root'
})
export class WalletService {
  private userMoneySource = new BehaviorSubject<number>(0)
  userMoney$ = this.userMoneySource.asObservable()

  private API_URL = environment.USERS_API;

  private JWT_TOKEN = this.cookie.get(TokenFields.ACCESS_TOKEN);

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${this.JWT_TOKEN}`,
    }),
  };

  constructor(
    private httpClient: HttpClient,
    private cookie: CookieService) { }

  updateUserMoney(amount: number): void {
    this.userMoneySource.next(amount);
  }

  fetchUserWalletInfo():Observable<WalletResponse> {
    return this.httpClient.get<any>(`${this.API_URL}/wallet`, this.httpOptions)
  }

  addTransaction(transactionRequest: TransactionRequest): Observable<WalletResponse> {
    return this.httpClient.post<any>(`${this.API_URL}/wallet/transaction`,
      transactionRequest,
      this.httpOptions)
  }
}
