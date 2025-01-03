import { Injectable } from '@angular/core';
import { environment } from "../../../environments/environment";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, tap } from "rxjs";
import { GetJockeysResponse } from "../../models/jockey/GetJockeysResponse";
import { GetHorseResponse } from "../../models/horse/GetHorseResponse";
import { GetJockeyResponse } from "../../models/jockey/GetJockeyResponse";
import { TokenFields } from "../../models/auth/TokenFields";
import { CookieService } from "ngx-cookie-service";

@Injectable({
  providedIn: 'root'
})
export class JockeyService {
  private API_URL = environment.DATASTORE_API;
  private WEBCRAWLER_URL = environment.WEBCRAWLER

  private JWT_TOKEN = this.cookie.get(TokenFields.ACCESS_TOKEN);

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${this.JWT_TOKEN}`,
    }),
  };

  constructor(private httpClient: HttpClient, private cookie: CookieService) { }

  insertJockeysViaWebcrawler() {
    return this.httpClient
      .get<any>(`${this.WEBCRAWLER_URL}/crawler/jockeys`, this.httpOptions)
  }

  fetchJockeys(offset: number, max: number): Observable<GetJockeysResponse> {
    return this.httpClient
      .get<any>(
        `${this.API_URL}/jockeys?offset=${offset}&max=${max}`,
        this.httpOptions
      )
  }

  getJockeyById(id: number): Observable<GetJockeyResponse> {
    return this.httpClient
      .get<any>(`${this.API_URL}/jockeys/${id}`, this.httpOptions)
  }
}
