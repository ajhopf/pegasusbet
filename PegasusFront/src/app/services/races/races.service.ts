import { Injectable } from '@angular/core';
import { environment } from "../../../environments/environment";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { GetJockeysResponse } from "../../models/jockey/GetJockeysResponse";
import { GetRacesResponse } from "../../models/races/GetRacesResponse";
import { TokenFields } from "../../models/auth/TokenFields";
import { CookieService } from "ngx-cookie-service";
import { CreateRaceRequest } from "../../models/races/CreateRaceRequest";
import { GetRaceResponse } from '../../models/races/GetRaceResponse'

@Injectable({
  providedIn: 'root'
})
export class RacesService {
  private API_URL = environment.DATASTORE_API;

  private JWT_TOKEN = this.cookie.get(TokenFields.ACCESS_TOKEN);

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${this.JWT_TOKEN}`,
    }),
  };

  constructor(private httpClient: HttpClient, private cookie: CookieService) { }

  fetchRaces(offset: number, max: number): Observable<GetRacesResponse> {
    return this.httpClient
      .get<any>(
        `${this.API_URL}/races?offset=${offset}&max=${max}`, this.httpOptions
      )
  }

  createRace(createRaceRequest: CreateRaceRequest) {
    return this.httpClient.post<any>(
      `${this.API_URL}/races`,
      createRaceRequest,
      {
        ...this.httpOptions
      }
    )
  }

  fetchRaceByRaceHorseJockeyId(id: number): Observable<GetRaceResponse> {
    return this.httpClient.get<any>(`${this.API_URL}/races/${id}`, this.httpOptions)
  }
}
