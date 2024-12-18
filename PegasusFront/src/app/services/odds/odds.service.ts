import { Injectable } from '@angular/core';
import { environment } from "../../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { GetOddByRaceHorseJockeyResponse } from "../../models/odds/GetOddByRaceHorseJockeyResponse";

@Injectable({
  providedIn: 'root'
})
export class OddsService {

  private API_URL = environment.DATASTORE_API;

  constructor(private httpClient: HttpClient) { }

  fetchOddsByRaceHorseJockeyId(id: number): Observable<GetOddByRaceHorseJockeyResponse> {
    return this.httpClient
      .get<any>(
        `${this.API_URL}/odds/${id}`
      )
  }
}
