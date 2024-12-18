import { Injectable } from '@angular/core';
import { environment } from "../../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { GetJockeysResponse } from "../../models/jockey/GetJockeysResponse";
import { GetRacesResponse } from "../../models/races/GetRacesResponse";

@Injectable({
  providedIn: 'root'
})
export class RacesService {

  private API_URL = environment.DATASTORE_API;

  constructor(private httpClient: HttpClient) { }

  fetchRaces(offset: number, max: number): Observable<GetRacesResponse> {
    return this.httpClient
      .get<any>(
        `${this.API_URL}/races?offset=${offset}&max=${max}`
      )
  }
}
