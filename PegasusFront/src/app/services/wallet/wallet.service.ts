import { Injectable } from '@angular/core';
import { environment } from "../../../environments/environment";
import { TokenFields } from "../../models/auth/TokenFields";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { CookieService } from "ngx-cookie-service";
import { Observable } from "rxjs";
import { WalletResponse } from "../../models/wallet/WalletResponse";

@Injectable({
  providedIn: 'root'
})
export class WalletService {
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

  fetchUserWalletInfo():Observable<WalletResponse> {
    return this.httpClient.get<any>(`${this.API_URL}/wallet`, this.httpOptions)
  }
}
