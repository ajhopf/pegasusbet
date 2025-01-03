import { Injectable } from '@angular/core';
import { environment } from "../../../environments/environment";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { GetOddByRaceHorseJockeyResponse } from "../../models/odds/GetOddByRaceHorseJockeyResponse";
import { TokenFields } from "../../models/auth/TokenFields";
import { CookieService } from "ngx-cookie-service";

@Injectable({
  providedIn: 'root'
})
export class OddsService {

  private API_URL = environment.DATASTORE_API;

  private JWT_TOKEN = this.cookie.get(TokenFields.ACCESS_TOKEN);

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${this.JWT_TOKEN}`,
    }),
  };

  constructor(private httpClient: HttpClient, private cookie: CookieService) { }

  fetchOddsByRaceHorseJockeyId(id: number): Observable<GetOddByRaceHorseJockeyResponse> {
    return this.httpClient
      .get<any>(
        `${this.API_URL}/odds/${id}`, this.httpOptions
      )
  }
}
