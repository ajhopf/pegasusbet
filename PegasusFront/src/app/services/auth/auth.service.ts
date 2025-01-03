import { Injectable } from '@angular/core';
import { environment } from "../../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

import { LoginResponse } from "../../models/auth/LoginResponse";
import { CookieService } from "ngx-cookie-service";

import { TokenFields } from "../../models/auth/TokenFields";
import { JwtHelperService } from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private USERS_API = environment.USERS_API;
  constructor(
    private httpClient: HttpClient,
    private cookieService: CookieService,
    private jwtHelper: JwtHelperService
  ) { }

  login (username: string, password: string): Observable<LoginResponse> {
    return this.httpClient
      .post<LoginResponse>(`${this.USERS_API}/api/login`, {username, password})
  }

  createUser(username: string, password: string): Observable<any> {
    return this.httpClient.post<any>(`${this.USERS_API}/user`, {username, password})
  }

  logout(){
    this.cookieService.deleteAll()
  }

  isAuthenticated(): boolean{
    const token = this.cookieService.get(TokenFields.ACCESS_TOKEN)

    if (!token) {
      return false;
    }

    return !this.jwtHelper.isTokenExpired(token)
  }

  isAdminAuthenticated(): boolean{
    const token = this.cookieService.get(TokenFields.ACCESS_TOKEN)

    if (!token) {
      return false;
    }

    const decodedToken = this.jwtHelper.decodeToken(token);

    return decodedToken?.roles?.includes('ROLE_ADMIN');
  }

}
